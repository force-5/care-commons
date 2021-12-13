package com.force5solutions.care.cc

class ContractorSupervisor {
    Date dateCreated
    Date lastUpdated

    Vendor vendor
    Person person
    static belongsTo = [vendor: Vendor]

    static constraints = {
       vendor(blank: false)
       person(validator:{val, obj ->
           if(!obj.person?.email || !obj.person?.phone){
               return "default.blank.message"
           }
       })
    }

    String toString() {
        person?.toString()
    }

    public static def findBySlid(String slid) {
        return (slid ? (ContractorSupervisor.createCriteria().get {person { eq('slid', slid) }}) : null)
    }

    public def getName() {
        "${person?.lastName ?: ''}, ${person?.firstName ?: ''}"
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

    public def getEmail() {
        person?.email
    }

    public def getSlid() {
        person?.slid
    }

    public def getFirstMiddleLastName() {
        person?.firstMiddleLastName
    }
}