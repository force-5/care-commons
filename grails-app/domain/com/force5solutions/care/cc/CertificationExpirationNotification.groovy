package com.force5solutions.care.cc

class CertificationExpirationNotification {

    WorkerCertification workerCertification
    int notificationPeriod

    static constraints = {
    workerCertification(nullable:false)
    notificationPeriod(nullable:false)
    }

    String toString() {
        return "Worker:${workerCertification.worker}/Certification:${workerCertification.certification}/Notification Period:$notificationPeriod"
    }

}
