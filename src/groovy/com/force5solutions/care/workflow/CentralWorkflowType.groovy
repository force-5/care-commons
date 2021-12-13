package com.force5solutions.care.workflow

import org.codehaus.groovy.grails.commons.ConfigurationHolder

public enum CentralWorkflowType {

    EMPLOYEE_PUBLIC_ACCESS_REQUEST("ACCESS APPROVAL", "employeeApprovalOldWorkflow"),
    EMPLOYEE_ACCESS_REQUEST("ACCESS APPROVAL", "employeeApprovalWorkflow"),
    EMPLOYEE_ACCESS_REVOKE("ACCESS REVOCATION", "employeeRevocationWorkflow"),
    EMPLOYEE_TERMINATION("TERMINATION", "employeeTerminationWorkflow"),
    EMPLOYEE_CANCEL_ACCESS_APPROVAL("CANCEL ACCESS APPROVAL", "employeeRevocationWorkflow"),
    EMPLOYEE_CANCEL_ACCESS_REVOCATION("CANCEL ACCESS REVOCATION", "employeeApprovalWorkflow"),

    CONTRACTOR_ACCESS_REQUEST("CONTRACTOR ACCESS APPROVAL", "contractorApprovalWorkflow"),
    CONTRACTOR_ACCESS_REVOKE("CONTRACTOR ACCESS REVOCATION", "contractorRevocationWorkflow"),
    CONTRACTOR_TERMINATION("CONTRACTOR TERMINATION", "contractorTerminationWorkflow"),
    CONTRACTOR_CANCEL_ACCESS_APPROVAL("CANCEL ACCESS APPROVAL", "contractorRevocationWorkflow"),
    CONTRACTOR_CANCEL_ACCESS_REVOCATION("CANCEL ACCESS REVOCATION", "contractorApprovalWorkflow"),

    CONTRACTOR_CLOUD_ADD_NEW_CONTRACTOR("CONTRACTOR CLOUD ADD NEW CONTRACTOR", "contractorCloudAddNewWorkerWorkflow"),
    CONTRACTOR_CLOUD_ADD_NEW_WORKER_CERTIFICATION("CONTRACTOR CLOUD ADD NEW WORKER CERTIFICATION", "contractorCloudAddNewWorkerCertificationWorkflow"),

    ADD_ENTITLEMENT_POLICY("ADD Entitlement Policy", "addEntitlementPolicyWorkflow"),
    UPDATE_ENTITLEMENT_POLICY("UPDATE Entitlement Policy", "updateEntitlementPolicyWorkflow"),
    CERTIFICATION_EXPIRATION_NOTIFICATION("CERTIFICATION EXPIRATION NOTIFICATION", "certificationExpirationNotificationWorkflow"),

    ACCESS_VERIFICATION("ACCESS VERIFICATION", "accessVerificationWorkflow"),
    ACCESS_GRANTED_BY_FEED('ACCESS GRANTED BY FEED', "accessGrantedByFeedWorkflow"),
    ACCESS_REVOKED_BY_FEED('ACCESS REVOKED BY FEED', "accessRevokedByFeedWorkflow"),
    PROVISIONER_DEPROVISIONER_TASKS_ON_ROLE_UPDATE("PROVISIONER DEPROVISIONER TASKS ON ROLE UPDATE", "provisionerDeprovisionerTasksOnRoleUpdateWorkflow")

    final String name
    final String workflowFilePathConfigProperty

    CentralWorkflowType(String name, String workflowFilePathConfigProperty) {
        this.name = name
        this.workflowFilePathConfigProperty = workflowFilePathConfigProperty
    }

    String toString() {
        return name
    }

    String getKey() {
        return name()
    }

    public String getWorkflowFilePath() {
        return ConfigurationHolder.config[workflowFilePathConfigProperty]
    }

    String getWorkflowProcessId() {
        String filePath = getWorkflowFilePath()
        String processId = filePath.tokenize('/').last() - '.rf'
        return ('com.force5solutions.care.workflow.' + processId)
    }

    public static CentralWorkflowType findKey(String nameString) {
        return (nameString ? CentralWorkflowType.values().find {CentralWorkflowType type -> type.name.equals(nameString)} : null)
    }

    public static List<CentralWorkflowType> list() {
        return [CERTIFICATION_EXPIRATION_NOTIFICATION,
                UPDATE_ENTITLEMENT_POLICY,
                ADD_ENTITLEMENT_POLICY,
                CONTRACTOR_CLOUD_ADD_NEW_WORKER_CERTIFICATION,
                CONTRACTOR_CLOUD_ADD_NEW_CONTRACTOR,
                CONTRACTOR_CANCEL_ACCESS_REVOCATION,
                CONTRACTOR_CANCEL_ACCESS_APPROVAL,
                CONTRACTOR_TERMINATION,
                CONTRACTOR_ACCESS_REVOKE,
                CONTRACTOR_ACCESS_REQUEST,
                EMPLOYEE_CANCEL_ACCESS_REVOCATION,
                EMPLOYEE_CANCEL_ACCESS_APPROVAL,
                EMPLOYEE_TERMINATION,
                CERTIFICATION_EXPIRATION_NOTIFICATION,
                EMPLOYEE_ACCESS_REVOKE,
                EMPLOYEE_ACCESS_REQUEST,
                ACCESS_VERIFICATION,
                ACCESS_GRANTED_BY_FEED,
                ACCESS_REVOKED_BY_FEED,
                PROVISIONER_DEPROVISIONER_TASKS_ON_ROLE_UPDATE]
    }
}
