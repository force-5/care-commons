package com.force5solutions.care.feed

class FeedRun {

    String feedName;

    Date startTime = new Date()
    Date endTime
    List<FeedRunReportMessage> reportMessages = []

    def beforeInsert = {
        endTime = new Date()
    }

    def beforeUpdate = {
        endTime = new Date()
    }

    static hasMany = [reportMessages: FeedRunReportMessage]

    static constraints = {
        endTime(nullable: true)
    }

    List<FeedRunReportMessage> getCreateMessages() {
        return ((reportMessages) ? (reportMessages.findAll {it.operation == FeedOperation.CREATE}) : [])
    }

    List<FeedRunReportMessage> getDeleteMessages() {
        return ((reportMessages) ? (reportMessages.findAll {it.operation == FeedOperation.DELETE}) : [])
    }

    List<FeedRunReportMessage> getUpdateMessages() {
        return ((reportMessages) ? (reportMessages.findAll {it.operation == FeedOperation.UPDATE}) : [])
    }

    List<FeedRunReportMessage> getProcessMessages() {
        return ((reportMessages) ? (reportMessages.findAll {it.operation == FeedOperation.PROCESS}) : [])
    }

    List<FeedRunReportMessage> getExceptionMessages() {
        return ((reportMessages) ? (reportMessages.findAll {it.type == FeedReportMessageType.EXCEPTION}) : [])
    }

    List<FeedRunReportMessage> getErrorMessages() {
        return ((reportMessages) ? (reportMessages.findAll {it.type == FeedReportMessageType.ERROR}) : [])
    }

    List<FeedRunReportMessage> getInfoMessages() {
        return ((reportMessages) ? (reportMessages.findAll {it.type in [FeedReportMessageType.INFO, FeedReportMessageType.WARNING]}) : [])
    }
}
