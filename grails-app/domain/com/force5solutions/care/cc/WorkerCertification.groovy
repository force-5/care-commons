package com.force5solutions.care.cc

import java.text.SimpleDateFormat

class WorkerCertification {
    static belongsTo = [worker: Worker]
    static hasMany = [affidavits: CentralDataFile]
    Date dateCreated
    Date lastUpdated

    Boolean isApproved = true
    Certification certification
    Date dateCompleted
    String subType
    String provider
    CertificationStatus testStatus
    CertificationStatus trainingStatus
    Date cachedExpiry
    def sessionFactory

    static transients = ["fudgedExpiry", "expiry", "cachedExpiry", "currentStatus", "active", "expired"]

    String toString() {
        certification.toString()
    }

    String toDetailString() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy")
        String detailString = ""
        if (subType) {detailString += subType}
        if (dateCompleted) {detailString += "" + sdf.format(dateCompleted)}
        if (trainingStatus) {detailString += "" + sdf.format(trainingStatus?.date) + trainingStatus?.status + trainingStatus?.provider}
        if (testStatus) {detailString += "" + sdf.format(testStatus?.date) + testStatus?.status + testStatus?.provider}
        return detailString
    }

    static constraints = {
        subType(nullable: true)
        provider(nullable: true)
        testStatus(nullable: true)
        trainingStatus(nullable: true)
        dateCompleted(nullable: true)
    }

    public Date getFudgedExpiry() {
        Date expiryDate = expiry
        if (expiryDate && certification?.expirationOffset) {
            expiryDate = expiryDate - certification?.expirationOffset
        }
        return expiryDate
    }

    public boolean isExpired() {
        Date expiryDate = getFudgedExpiry();
        Date currentDate = new Date();
        return (expiryDate < currentDate)
    }

    public boolean isActive() {
        Date expiryDate = getFudgedExpiry()
        Date currentDate = new Date()
        return (expiryDate && (currentDate < expiryDate))
    }

    public Date getExpiry() {
        if (cachedExpiry) {return cachedExpiry}
        Date certificationDate = dateCompleted
        Date expiryDate
        Date date
        Set<Course> effectiveCourses = []
        if (certificationDate) {
            if (certification.courses) {
                certification.courses.each {course ->
                    if (course in worker.courses*.course) {
                        effectiveCourses.add(course)
                    }
                }
                expiryDate = effectiveCourses.max {it.endDate}?.endDate
            }
            if (certification.quarterly) {
                date = DateUtils.getLastDayOfNextQuarter(certificationDate.clone())
                if ((date < expiryDate) || (!expiryDate)) {
                    expiryDate = date
                }
            } else if (certification.period && certification.periodUnit) {
                use(org.codehaus.groovy.runtime.TimeCategory) {
                    switch (this.certification.periodUnit) {
                        case PeriodUnit.YEARS:
                            date = certificationDate + certification.period.years
                            break;
                        case PeriodUnit.MONTHS:
                            date = certificationDate + certification.period.months
                            break;
                        case PeriodUnit.WEEKS:
                            date = certificationDate + certification.period.weeks
                            break;
                        case PeriodUnit.DAYS:
                            date = certificationDate + certification.period.days
                            break;
                    }
                }
                if ((date < expiryDate) || (!expiryDate)) {
                    expiryDate = date
                }
            }
        }
        if (dateCompleted && !expiryDate) {
            use(org.codehaus.groovy.runtime.TimeCategory) {
                expiryDate = dateCompleted + 10.years
            }
        }
        cachedExpiry = expiryDate
        return expiryDate
    }

    public String getCurrentStatus() {
        String status
        if (!dateCompleted) {
            status = "Pending"
        } else {
            if (new Date() < fudgedExpiry) {
                Integer daysToExpire = fudgedExpiry - new Date()
                if (daysToExpire > 90) {
                    status = "Completed"
                }
                else {
                    status = "${daysToExpire} Days"
                }
            }
            else {
                status = "Expired"
            }
        }

        return status
    }

    boolean equals(o) {
        if (this.is(o)) return true;
        if (!(o.instanceOf(WorkerCertification.class))) return false;
        WorkerCertification workerCertification = (WorkerCertification) o;
        return (this.ident() == workerCertification.ident())
    }

}
