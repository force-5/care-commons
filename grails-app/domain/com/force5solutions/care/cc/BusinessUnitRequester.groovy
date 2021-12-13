package com.force5solutions.care.cc

import org.codehaus.groovy.grails.commons.ConfigurationHolder

class BusinessUnitRequester {
    static config = ConfigurationHolder.config
    Date dateCreated
    Date lastUpdated

    Person person
    BusinessUnit unit

//    Set<Certification> getMissingCertifications() {
//        LocationType locationType = LocationType.findByType(LOCATION_TYPE_CARE_SYSTEM)
//        Location location = Location.findByLocationType(locationType)
//        return location.sponsorCertifications
//    }

    def beforeInsert = {
        person.email = AppUtil.getEmailFromSlid(person.slid)
    }

    def beforeUpdate = {
        person.email = AppUtil.getEmailFromSlid(person.slid)
    }

    public static def findBySlid(String slid) {
        return (slid ? (BusinessUnitRequester.createCriteria().get {person { eq('slid', slid) }}) : null)
    }

    public static def countBySlid(String slid) {
        return (slid ? (BusinessUnitRequester.createCriteria().count {person { eq('slid', slid) }}) : null)
    }

    public static getInheritedContractors(String slid) {
        BusinessUnitRequester businessUnitRequester = BusinessUnitRequester?.findBySlid(slid)
        List<Contractor> filteredContractors = []
        List<BusinessUnitRequester> businessUnitRequesterList = []
        if (businessUnitRequester) {
            List<Worker> subordinates = EmployeeSupervisor.getInheritedSubordinates((EmployeeSupervisor.findBySlid(businessUnitRequester.slid)).slid)
            subordinates.each { Worker worker ->
                BusinessUnitRequester unitRequester = BusinessUnitRequester.findBySlid(worker.slid)
                if (unitRequester) {
                    businessUnitRequesterList.add(unitRequester)
                }
            }
            businessUnitRequesterList.add(businessUnitRequester)
        }
        if (businessUnitRequesterList) {
            Contractor.list().each { Contractor contractor ->
                if (businessUnitRequesterList.any {it in contractor.businessUnitRequesters}) {
                    filteredContractors.add(contractor)
                }
            }
        }
        return filteredContractors
    }

    String toString() {
        "${person.lastName ?: ''}, ${person.firstName ?: ''} ${person.middleName ?: ''}"
    }

    String toDetailString() {
        return (person.toDetailString() + "${unit}")
    }

    public def getName() {
        "${person.lastName ?: ''}, ${person.firstName ?: ''}"
    }

    public def getFirstName() {
        person?.firstName
    }

    public def getLastName() {
        person?.lastName
    }

    public def getMiddleName() {
        person?.middleName
    }

    public def getNotes() {
        person?.notes
    }

    public def getPhone() {
        person?.phone
    }

    public def getFirstMiddleLastName() {
        person?.firstMiddleLastName
    }

    public def getSlid() {
        person?.slid
    }

    public def getEmail() {
        person?.email
    }

    boolean equals(o) {
        if (this.is(o)) return true;
        if (!(o.instanceOf(BusinessUnitRequester.class))) return false;
        BusinessUnitRequester businessUnitRequester = (BusinessUnitRequester) o;
        return (this.ident() == businessUnitRequester.ident())
    }

}