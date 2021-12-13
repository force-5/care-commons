package com.force5solutions.care.cc

class WorkerEntitlementArchive {

    Long workerId
    Long apsWorkflowTaskId
    Long centralWorkflowTaskId
    String workerFirstName
    String workerMiddleName
    String workerLastName
    String workerSlid
    String entitlementId
    String entitlementName
    String entitlementAlias
    String userResponse
    String entitlementPolicyType
    String entitlementOrigin
    String notes
    String entitlementProvisionerSlid
    String entitlementDeProvisionerSlid
    Date actionDate
    String actionType
    Date dateCreated
    Date lastUpdated
    String entitlementAttributes
    String evidenceIds


    static constraints = {
        workerFirstName(nullable: true)
        workerMiddleName(nullable: true, blank: true)
        workerLastName(nullable: true)
        workerSlid(nullable: true)
        entitlementId(nullable: true)
        entitlementName(nullable: true)
        entitlementAlias(nullable: true)
        userResponse(nullable: true)
        entitlementOrigin(nullable: true)
        entitlementPolicyType(nullable: true)
        notes(nullable: true)
        entitlementProvisionerSlid(nullable: true)
        entitlementDeProvisionerSlid(nullable: true)
        entitlementAttributes(nullable: true)
        actionDate(nullable: true)
        actionType(nullable: true)
        dateCreated(nullable: true)
        lastUpdated(nullable: true)
        apsWorkflowTaskId(nullable: true)
        centralWorkflowTaskId(nullable: true)
        evidenceIds(nullable: true, blank: true)
    }

    static mapping = {
        entitlementName type: 'text'
        entitlementAlias type: 'text'
        notes type: 'text'
        entitlementAttributes type: 'text'
        evidenceIds type: 'text'
    }
}
