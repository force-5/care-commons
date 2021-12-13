package com.force5solutions.care.cc

enum EntitlementRoleAccessStatus {

    PENDING_ACCESS_APPROVAL('Pending Access Approval'),
    PENDING_ACCESS_REVOCATION('Pending Access Revocation'),
    PENDING_TERMINATION('Pending Termination'),
    REJECTED('Rejected'),
    ACTIVE('Active'),
    REVOKED('Revoked'),
    PENDING_PROVISIONER_DEPROVISIONER_TASKS_ON_ROLE_UPDATE('Pending Provisioner Deprovisioner Task on Role Update'),
    TERMINATED_FOR_CAUSE('Terminated for Cause'),
    ERROR('Error'),
    TIME_OUT('Time out')

    EntitlementRoleAccessStatus(String name) {
        this.name = name
    }

    String toString() {
        return name
    }

    static list() {
        [PENDING_ACCESS_APPROVAL, PENDING_ACCESS_REVOCATION, PENDING_TERMINATION, REJECTED, ACTIVE, REVOKED, PENDING_PROVISIONER_DEPROVISIONER_TASKS_ON_ROLE_UPDATE, TERMINATED_FOR_CAUSE, ERROR, TIME_OUT]
    }


    static EntitlementRoleAccessStatus get(String name) {
        return (EntitlementRoleAccessStatus.list().find {it.name == name})
    }


    String getKey() {
        return name()
    }

    private final String name

    public static EntitlementRoleAccessStatus getError() {
        return EntitlementRoleAccessStatus.ERROR
    }

    public static EntitlementRoleAccessStatus getPendingApproval() {
        return EntitlementRoleAccessStatus.PENDING_ACCESS_APPROVAL
    }

    public static EntitlementRoleAccessStatus getPendingRevocation() {
        return EntitlementRoleAccessStatus.PENDING_ACCESS_REVOCATION
    }

    public static EntitlementRoleAccessStatus getPendingTermination() {
        return EntitlementRoleAccessStatus.PENDING_TERMINATION
    }

    public static EntitlementRoleAccessStatus getRejected() {
        return EntitlementRoleAccessStatus.REJECTED
    }

    public static EntitlementRoleAccessStatus getActive() {
        return EntitlementRoleAccessStatus.ACTIVE
    }

    public static EntitlementRoleAccessStatus getRevoked() {
        return EntitlementRoleAccessStatus.REVOKED
    }

    public static EntitlementRoleAccessStatus getTerminatedForCause() {
        return EntitlementRoleAccessStatus.TERMINATED_FOR_CAUSE
    }

    public static EntitlementRoleAccessStatus getTimeOut() {
        return EntitlementRoleAccessStatus.TIME_OUT
    }

    public static EntitlementRoleAccessStatus getPendingProvisionerDeprovisionerTasksOnRoleUpdate() {
        return EntitlementRoleAccessStatus.PENDING_PROVISIONER_DEPROVISIONER_TASKS_ON_ROLE_UPDATE
    }

    public static List<EntitlementRoleAccessStatus> getStatusesToBeCheckedWhileDeletionOfRoles(){
        return [ACTIVE, PENDING_ACCESS_APPROVAL, PENDING_ACCESS_REVOCATION, PENDING_PROVISIONER_DEPROVISIONER_TASKS_ON_ROLE_UPDATE]
    }
}