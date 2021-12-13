package com.force5solutions.care.feed

import groovy.sql.GroovyRowResult
import groovy.sql.Sql
import org.apache.commons.logging.LogFactory
import com.force5solutions.care.common.RunTimeStampHelper

abstract class DatabaseFeedService implements FeedService {

    FeedRun feedRun = new FeedRun(feedName: feedName)
    private static final log = LogFactory.getLog(this)
    public String feedName;
    public String driver;
    public String url;
    public String query;

    List<GroovyRowResult> getRows() {
        RunTimeStampHelper runTimeStampHelper = new RunTimeStampHelper()
        Map queryParams = getQueryParameters();
        List<GroovyRowResult> rows = getDataAndErrorReport(queryParams)
        log.debug "Time taken to fetch the rows/data from the REMOTE database: ${runTimeStampHelper.stamp()}"
        println "Time taken to fetch the rows/data from the REMOTE database: ${runTimeStampHelper.stamp()}"
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

        List<GroovyRowResult> rows = []
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
            prepareFeedReportAndSave();
        } catch (Throwable t) {
            addMessageToFeedRun(t, FeedReportMessageType.ERROR)
            prepareFeedReportAndSave();
        }
        try {
            startWorkflows()
            log.debug "After starting work-flows"
        } catch (Throwable t) {
            addMessageToFeedRun(t, FeedReportMessageType.ERROR)
            prepareFeedReportAndSave();
        }
        return feedRun
    }


    List<GroovyRowResult> getDataAndErrorReport(Map queryParameters = [:]) {
        List<GroovyRowResult> rows = []
        queryParameters?.each {String key, String value ->
            query = query.replaceAll("###${key}###", value)
        }
        log.debug "*********Query: " + query
        Sql sql = Sql.newInstance(url, driver)
        rows = sql.rows(query)
        log.debug "Number of rows are ${rows.size()}"
        return rows;
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
