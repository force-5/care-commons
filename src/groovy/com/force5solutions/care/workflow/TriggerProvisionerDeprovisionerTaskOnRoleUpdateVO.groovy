package com.force5solutions.care.workflow


class TriggerProvisionerDeprovisionerTaskOnRoleUpdateVO {
    String guid
    Long workerEntitlementRoleId

    TriggerProvisionerDeprovisionerTaskOnRoleUpdateVO() {}

    TriggerProvisionerDeprovisionerTaskOnRoleUpdateVO(String guid, Long workerEntitlementRoleId) {
        this.guid = guid
        this.workerEntitlementRoleId = workerEntitlementRoleId
    }
}