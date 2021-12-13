package com.force5solutions.care.common
class LogMessage {

    Date dateCreated
    Date lastUpdated

    String module
    String message
    String stackTrace
    static constraints = {
        stackTrace(nullable: true, maxSize: 8000, blank:true)
        dateCreated(nullable: true)
        message(nullable: false)
        module(nullable: false)
    }

    def beforeInsert = {
        if (stackTrace?.length() > 8000) {
            stackTrace = stackTrace.substring(stackTrace.length() - 7999)
        }
    }

}
