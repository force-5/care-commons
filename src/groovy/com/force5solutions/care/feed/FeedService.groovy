package com.force5solutions.care.feed

public interface FeedService {
    def process(def feedVO);
    Map getQueryParameters();
    void preProcess();
    void postProcess();
    List getVOs(def rows);
    List<FeedRunReportMessage> getFeedRunReportMessages();
    void preValidate();
    void startWorkflows();
}