package com.force5solutions.care.cc

class WorkerProfileArchive {

    Long workerImageId
    Long workerId
    Long pernr
    String slid
    String fullName
    String firstName
    String middleName
    String lastName
    String email
    String phone
    String notes
    String personStatus
    String orgUnitNum
    String orgUnitDesc
    String workerNumber
    String title
    String persAreaNum
    String department
    String persSubAreaNum
    String persSubAreaDesc
    String officePhoneNum
    String cellPhoneNum
    String pagerNum
    String supervisorSlid
    String supvFullName
    String supvSupvSlid
    String supvSupvFullName
    String badgeNumber

    Integer birthDay
    Integer birthMonth
    Long primeVendorId
    Long subVendorId
    Long subSupervisorId
    String formOfId
    String businessUnitRequesterSlid
    String businessUnit
    String vendorName
    String emergencyContact
    String personalEmail

    Date dateCreated
    Date lastUpdated


    static constraints = {
        pernr(nullable: true)
        slid(nullable: true)
        fullName(nullable: true)
        firstName(nullable: true)
        middleName(nullable: true)
        lastName(nullable: true)
        personStatus(nullable: true)
        orgUnitNum(nullable: true)
        orgUnitDesc(nullable: true)
        workerNumber(nullable: true)
        title(nullable: true)
        persAreaNum(nullable: true)
        department(nullable: true)
        persSubAreaNum(nullable: true)
        persSubAreaDesc(nullable: true)
        officePhoneNum(nullable: true)
        cellPhoneNum(nullable: true)
        pagerNum(nullable: true)
        supervisorSlid(nullable: true)
        supvFullName(nullable: true)
        supvSupvSlid(nullable: true)
        supvSupvFullName(nullable: true)
        workerImageId(nullable: true)
        email(nullable: true)
        phone(nullable: true)
        notes(nullable: true)
        badgeNumber(nullable: true)
        birthDay(nullable: true)
        birthMonth(nullable: true)
        primeVendorId(nullable: true)
        subVendorId(nullable: true)
        subSupervisorId(nullable: true)
        formOfId(nullable: true)
        businessUnitRequesterSlid(nullable: true)
        businessUnit(nullable: true)
        vendorName(nullable: true)
        emergencyContact(nullable: true)
        personalEmail(nullable: true)
    }

    String toString(){
        return "${workerId} : ${lastName}, ${firstName}"
    }

    static mapping = {
        notes type: 'text'
        department type: 'text'
        orgUnitDesc type: 'text'
        persSubAreaDesc type: 'text'
    }
}
