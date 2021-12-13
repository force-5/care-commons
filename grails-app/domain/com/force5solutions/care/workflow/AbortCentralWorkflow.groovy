package com.force5solutions.care.workflow

class AbortCentralWorkflow {

    String workflowGuid
    Boolean isAborted = false

    Date dateCreated
    Date lastUpdated

    static constraints = {
        workflowGuid(unique: true)
        dateCreated(nullable: true)
        lastUpdated(nullable: true)
    }
}
