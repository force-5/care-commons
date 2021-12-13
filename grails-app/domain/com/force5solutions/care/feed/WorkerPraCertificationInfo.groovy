package com.force5solutions.care.feed

class WorkerPraCertificationInfo {
    Long pernr
    Date lastBackgroundDate
    String slid

    WorkerPraCertificationInfo() {}

    WorkerPraCertificationInfo(Long pernr, Date lastBackgroundDate) {
        this.pernr = pernr
        this.lastBackgroundDate = lastBackgroundDate
        this.lastBackgroundDate?.clearTime()
    }

    WorkerPraCertificationInfo(String slid, Date lastBackgroundDate) {
        this.slid = slid
        this.lastBackgroundDate = lastBackgroundDate
        this.lastBackgroundDate?.clearTime()
    }

    String toString() {
        return "${pernr ?: ''} : ${lastBackgroundDate?.myFormat()} : ${slid ?: ''}"
    }

    static constraints = {
        pernr(nullable: true)
        slid(nullable: true)
    }
}
