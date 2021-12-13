package com.force5solutions.care.workflow

import static com.force5solutions.care.common.CareConstants.REPORT_FIELD_EMPTY_TOKEN

class WorkflowVO {
    String workflowType
    String system
    String actorSlid
    String nodeName
    Long taskId
    Date taskCreated
    Date taskUpdated
    String response
    String status
    String message
    Long documentsCount
    String workerName

    WorkflowVO(WorkflowTask workflowTask, String system) {
        workflowType = workflowTask?.workflowType?.toString() ?: REPORT_FIELD_EMPTY_TOKEN
        this.system = system
        taskId = workflowTask?.id
        documentsCount = workflowTask?.documents?.size()
        actorSlid = workflowTask?.actorSlid ?: REPORT_FIELD_EMPTY_TOKEN
        nodeName = workflowTask?.nodeName ?: REPORT_FIELD_EMPTY_TOKEN
        taskCreated = workflowTask?.dateCreated
        taskUpdated = workflowTask?.lastUpdated
        response = workflowTask?.response ?: REPORT_FIELD_EMPTY_TOKEN
        status = workflowTask?.status?.toString() ?: REPORT_FIELD_EMPTY_TOKEN
        message = workflowTask?.message?.toString() ?: REPORT_FIELD_EMPTY_TOKEN
        workerName = workflowTask?.worker?.name ?: REPORT_FIELD_EMPTY_TOKEN
    }
}
