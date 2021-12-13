package com.force5solutions.care.feed

class FeedTrigger {
    Date startTime = new Date()
    Date endTime
    boolean isManual = false
    List<FeedRun> feedRuns = []

    def beforeInsert ={
        endTime = new Date()
    }

    def beforeUpdate = {
        endTime = new Date()
    }

    static hasMany = [feedRuns: FeedRun]

    static constraints = {
        endTime(nullable: true)
    }

}
