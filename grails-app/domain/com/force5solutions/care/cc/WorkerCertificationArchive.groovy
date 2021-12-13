package com.force5solutions.care.cc

class WorkerCertificationArchive {

    Date dateCreated
    Date lastUpdated

    String certificationIds
    String certificationNames
    Long workerId
    String workerFirstName
    String workerMiddleName
    String workerLastName
    String workerSlid

    static constraints = {
        dateCreated(nullable: true)
        lastUpdated(nullable: true)
        certificationIds(nullable: true)
        workerId(nullable: true)
        certificationNames(nullable: true)
        workerFirstName(nullable: true)
        workerMiddleName(nullable: true)
        workerLastName(nullable: true)
        workerSlid(nullable: true)
    }

    static mapping = {
        certificationNames type: 'text'
    }
}
