package com.force5solutions.care.workflow

class CentralWorkflowTaskPermittedSlid {
    CentralWorkflowTask task
    String slid
    String guid = UUID.randomUUID().toString()
    Date dateCreated
    Date lastUpdated
    boolean isArchived = false

    void setSlid(String slid) {
        this.slid = slid?.toUpperCase()
    }

    static constraints = {
        dateCreated(nullable: true)
        lastUpdated(nullable: true)
    }

    static belongsTo = [task: CentralWorkflowTask]

    public static void markArchived(CentralWorkflowTask task, String slid) {
        CentralWorkflowTaskPermittedSlid userSpecificTaskArchiveStatus = CentralWorkflowTaskPermittedSlid.findBySlidAndTask(slid, task);
        userSpecificTaskArchiveStatus?.isArchived = true
        userSpecificTaskArchiveStatus?.s();
    }
}
