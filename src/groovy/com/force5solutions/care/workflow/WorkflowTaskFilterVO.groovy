package com.force5solutions.care.workflow


class WorkflowTaskFilterVO {
    String workflowType
    Long workerId
    Long securityRoleId
    Long entitlementPolicyId
    String entitlementId
    String currentNodeName
    String actorSlid

    WorkflowTaskFilterVO() {}

    WorkflowTaskFilterVO(String workflowType, Long workerId, Long entitlementPolicyId, String entitlementId, String currentNodeName = null, Long securityRoleId = null, String actorSlid = null) {
        this.workflowType = workflowType
        this.workerId = workerId
        this.entitlementPolicyId = entitlementPolicyId
        this.entitlementId = entitlementId
        this.currentNodeName = currentNodeName
        this.securityRoleId = securityRoleId
        this.actorSlid = actorSlid
    }
}