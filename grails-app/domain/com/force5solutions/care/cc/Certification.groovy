package com.force5solutions.care.cc

class Certification {
    Date dateCreated
    Date lastUpdated
    String name
    String description
    Integer period
    PeriodUnit periodUnit
    boolean isApproved = true
    boolean quarterly
    boolean trainingRequired
    boolean testRequired
    boolean affidavitRequired
    boolean subTypeRequired
    boolean providerRequired
    String subTypes
    Integer expirationOffset = 0
    String providers
    String testProviders
    String trainingProviders
    Set<Course> courses = []

    static hasMany = [courses: Course]

    static transients = ['notificationPeriods']

    static constraints = {
        subTypes(nullable: true)
        providers(nullable: true)
        name(blank: false, unique: true)
        description(nullable: true)
        quarterly(nullable: true)
        period(nullable: true, min: 1)
        periodUnit(nullable: true, inList: PeriodUnit.list())
        trainingRequired(nullable: true)
        testRequired(nullable: true)
        affidavitRequired(blank: true)
        testProviders(nullable: true)
        trainingProviders(nullable: true)
    }

    String toString() {
        name
    }

    boolean equals(o) {
        if (this.is(o)) return true;
        if (!(o.instanceOf(Certification.class))) return false;
        Certification certification = (Certification) o;
        return (this.ident() == certification.ident())
    }

    public List<Integer> getNotificationPeriods() {
        CertificationExpirationNotificationTemplate.findNotificationPeriodsForCertification(this)
    }
}
