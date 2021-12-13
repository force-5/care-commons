package com.force5solutions.care.cc

class WorkerEntitlementRoleArchive {

    Long workerId
    String workerFirstName
    String workerMiddleName
    String workerLastName
    String workerSlid
    String entitlementRoleId
    String entitlementRoleName
    String entitlementRoleAlias
    String userResponse
    String entitlementRoleTypes
    String entitlementRoleOrigin
    String notes
    Date actionDate
    String actionType
    String activeEntitlementRoles
    Date dateCreated
    Date lastUpdated


    static constraints = {
        workerFirstName(nullable: true)
        workerMiddleName(nullable: true)
        workerLastName(nullable: true)
        workerSlid(nullable: true)
        entitlementRoleId(nullable: true)
        entitlementRoleName(nullable: true)
        entitlementRoleAlias(nullable: true)
        userResponse(nullable: true)
        entitlementRoleOrigin(nullable: true)
        entitlementRoleTypes(nullable: true)
        notes(nullable: true)
        actionDate(nullable: true)
        actionType(nullable: true)
        activeEntitlementRoles(nullable: true)
        dateCreated(nullable: true)
        lastUpdated(nullable: true)
    }
}
