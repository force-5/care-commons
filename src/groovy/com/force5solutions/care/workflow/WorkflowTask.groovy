package com.force5solutions.care.workflow

import com.force5solutions.care.cc.PeriodUnit

abstract class WorkflowTask {
    String workflowGuid
    Long workItemId
    Long droolsSessionId
    String nodeName
    long nodeId
    String responseForm
    Date dateCreated
    Date lastUpdated
    String response
    String message
    String actorSlid
    Date effectiveStartDate = new Date()
    PeriodUnit periodUnit
    Integer period
    String escalationTemplateId
    WorkflowTaskStatus status = WorkflowTaskStatus.NEW
    String responseElements
    Date actionDate = null
}
