package com.force5solutions.care.feed

class FeedRunReportMessage {
    List<FeedRunReportMessageDetail> details = []
    FeedRun feedRun
    FeedReportMessageType type
    FeedOperation operation
    Date dateCreated
    String message
    Integer numberOfRecords

    static hasMany = [details: FeedRunReportMessageDetail]
    static belongsTo = [feedRun: FeedRun]

    static constraints = {
        operation(nullable: true)
        message(nullable: true)
        numberOfRecords(nullable: true)
        dateCreated(nullable: true)
        message(maxSize: 8000)
    }

    String toString() {
        String toStringValue = operation ? operation.message : type ? "$type: " : ''
        if (operation && (message || (numberOfRecords != null))) {
            toStringValue += ' : '
        }
        if (message) {
            toStringValue += message
        }
        if (message && (numberOfRecords != null)) {
            toStringValue += ' - '
        }
        if (numberOfRecords != null) {
            toStringValue += numberOfRecords
        }
        return toStringValue
    }
}
