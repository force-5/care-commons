package com.force5solutions.care.feed

class ContractorHrInfo {
    String contractorNumber
    String contractorSlid
    String firstName
    String lastName
    String email
    String businessUnit
    String supervisorSlid
    String vendorName
    String emergencyContact
    String personalEmail

    static constraints = {
        contractorNumber(nullable: true)
        contractorSlid(nullable: true)
        firstName(nullable: true)
        lastName(nullable: true)
        email(nullable: true)
        businessUnit(nullable: true)
        supervisorSlid(nullable: true)
        vendorName(nullable: true)
        emergencyContact(nullable: true)
        personalEmail(nullable: true)
    }
    static mapping = {
        version false
    }

    public String toString() {
        return contractorSlid
    }
}
