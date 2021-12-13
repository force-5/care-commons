package com.force5solutions.care.feed

class ContractorPraAndCourseInfo {
    String contractorNumber
    String contractorSlid
    String courseName
    Date startDate
    Date endDate

    String toString() {
        return "${courseName} : ${contractorSlid ? contractorSlid : contractorNumber}"
    }

    static constraints = {
        courseName(nullable: true)
        contractorSlid(nullable: true)
        startDate(nullable: true)
        endDate(nullable: true)
        contractorNumber(nullable: true)
    }
}
