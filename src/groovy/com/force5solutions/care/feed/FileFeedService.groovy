package com.force5solutions.care.feed

import com.force5solutions.care.common.RunTimeStampHelper
import org.apache.commons.logging.LogFactory

abstract class FileFeedService implements FeedService {

    FeedRun feedRun = new FeedRun(feedName: feedName)
    public static final log = LogFactory.getLog(this)
    public String feedName
    public String fileName

    List<String> getRows() {
        Map queryParams = getQueryParameters();
        List<String> rows = getDataAndErrorReport(queryParams)
        return rows
    }


    public FeedRun execute() {
        try {
            preValidate();
        } catch (Throwable t) {
            addMessageToFeedRun(t, FeedReportMessageType.ERROR)
            prepareFeedReportAndSave();
            return feedRun;
        }

        List<String> rows = []
        try {
            rows = getRows()
        } catch (Throwable t) {
            addMessageToFeedRun(t, FeedReportMessageType.ERROR)
            prepareFeedReportAndSave();
            return feedRun;
        }
        List feedVOs;

        try {
            feedVOs = getVOs(rows);
            log.debug "After creating the VOs"
        } catch (Throwable t) {
            addMessageToFeedRun(t, FeedReportMessageType.ERROR)
            prepareFeedReportAndSave();
            return feedRun;
        }

        try {
            preProcess();
            log.debug "After pre-processing ... "
        } catch (Throwable t) {
            addMessageToFeedRun(t, FeedReportMessageType.ERROR)
            prepareFeedReportAndSave();
            return feedRun;
        }

        RunTimeStampHelper runTimeStampHelper = new RunTimeStampHelper()
        feedVOs.each { def feedVO ->
            try {
                process(feedVO);
            } catch (Throwable t) {
                addMessageToFeedRun(t, FeedReportMessageType.ERROR)
            }
        }
        log.debug "Time taken to create objects from VOs: ${runTimeStampHelper.stamp()}"
        println "Time taken to create objects from VOs: ${runTimeStampHelper.stamp()}"

        try {
            postProcess()
            log.debug "After post-processing."
        } catch (Throwable t) {
            addMessageToFeedRun(t, FeedReportMessageType.ERROR)
            prepareFeedReportAndSave();
        }
        try {
            startWorkflows()
            log.debug "After starting work-flows"
            prepareFeedReportAndSave();
        } catch (Throwable t) {
            addMessageToFeedRun(t, FeedReportMessageType.ERROR)
            prepareFeedReportAndSave();
        }
        return feedRun
    }


    List<String> getDataAndErrorReport(Map queryParameters = [:]) {
        //TODO: Do something about the query parameter; You can get the user input from the config in the form of a string and then convert it into a map;
        //TODO: example: [<csvDelimitedTokenNumber>:<matchingValue>] .. [5:"CCA CIP - Physical"] - This means that the 6 CSV value should match 'CCA CIP - Physical'.
        List<String> rows = []
        File feedFile = new File(System.getProperty('java.io.tmpdir'), fileName)
        feedFile.eachLine { String line ->
            rows.add(line)
        }
        return rows
    }

    void prepareFeedReportAndSave() {
        List<FeedRunReportMessage> messages = getFeedRunReportMessages();
        messages.each {
            feedRun.addToReportMessages(it)
            it.feedRun = feedRun;
        }
        feedRun.feedName = feedName
        feedRun.s();
    }

    private void addMessageToFeedRun(Throwable t, FeedReportMessageType messageType) {
        t.printStackTrace()
        FeedRunReportMessage feedRunReportMessage = new FeedRunReportMessage(type: messageType);
        feedRunReportMessage.message = t.getMessage()
        feedRunReportMessage.numberOfRecords = 1
        feedRun.addToReportMessages(feedRunReportMessage)
        feedRunReportMessage.feedRun = feedRun;
    }
}
