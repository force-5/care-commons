package com.force5solutions.care.workflow

import com.force5solutions.care.common.SessionUtils
import com.force5solutions.care.cc.*
import com.force5solutions.care.common.CareConstants
import java.beans.XMLEncoder

class CentralWorkflowTask extends WorkflowTask {

    def employeeUtilService

    CentralWorkflowType workflowType
    Long workerEntitlementRoleId
    Long workerCertificationId
    Long workerId
    Long entitlementPolicyId
    CentralWorkflowTaskType type = CentralWorkflowTaskType.HUMAN
    String revocationType

    List<String> actions = []
    Set<CentralDataFile> documents = []
    // TODO : There is another problem here : this is a list of String, however, this is a list of SecurityRole in the template
    Set<String> securityRoles = []
    Set<CentralWorkflowTaskPermittedSlid> permittedSlids = []
    String provisionerDeprovisionerTaskOnRoleUpdateGuid

    static hasMany = [documents: CentralDataFile, actions: String, permittedSlids: CentralWorkflowTaskPermittedSlid, securityRoles: String]

//    static transients = ['workflowFilePath', 'workflowProcessId', 'parametersForMessageTemplate', 'confirmationLink', 'employeeListLink']
    static transients = ['workflowFilePath', 'workflowProcessId', 'abbreviatedCodeForGroupResponse', 'accessRequestedTask', 'accessRevokedTask']

    static mapping = {
        tablePerHierarchy false
        responseElements type: 'text'
    }

    static constraints = {
        escalationTemplateId(nullable: true)
        period(nullable: true)
        periodUnit(nullable: true)
        actorSlid(nullable: true)
        response(nullable: true)
        message(nullable: true, maxSize: 8000)
        workerEntitlementRoleId(nullable: true)
        workerId(nullable: true)
        workerCertificationId(nullable: true)
        entitlementPolicyId(nullable: true)
        responseForm(nullable: true)
        dateCreated(nullable: true)
        lastUpdated(nullable: true)
        revocationType(nullable: true)
        responseElements(nullable: true)
        actionDate(nullable: true)
        provisionerDeprovisionerTaskOnRoleUpdateGuid(nullable: true)
    }

    def isTaskForEmployee() {
        Worker worker = worker
        return worker instanceof Employee
    }

    def getWorkerEntitlementRole() {
        WorkerEntitlementRole workerEntitlementRole = WorkerEntitlementRole.get(workerEntitlementRoleId)
        return workerEntitlementRole
    }

    def getWorkerCertification() {
        WorkerCertification workerCertification = WorkerCertification.get(workerCertificationId)
        return workerCertification
    }

    def getWorker() {
        Worker worker = workerCertificationId ? workerCertification.worker : (workerEntitlementRoleId ? workerEntitlementRole.worker : (workerId ? Worker.get(workerId) : null))
        return worker
    }

    def getWorkerAsSupervisor() {
        Worker worker = this.worker
        return (worker ? EmployeeSupervisor.findBySlid(worker.slid) : null)
    }

    def getEntitlementRole() {
        return ((workerEntitlementRoleId) ? workerEntitlementRole.entitlementRole : null)
    }

    def getEntitlementPolicy() {
        return ((entitlementPolicyId) ? EntitlementPolicy.get(entitlementPolicyId) : null)
    }

    static List<CentralWorkflowTask> getPermittedTasks() {
        List<CentralWorkflowTask> tasks = CentralWorkflowTask.findAllByTypeAndStatus(CentralWorkflowTaskType.HUMAN, WorkflowTaskStatus.NEW,
                [fetch: [permittedSlids: 'eager', securityRoles: 'eager']])
        List<CentralWorkflowTask> permittedTasksList = []
        tasks.each { CentralWorkflowTask task ->
            if (hasPermission(task)) {
                permittedTasksList.add(task)
            }
        }
        return permittedTasksList
    }

    static List<CentralWorkflowTask> getPermittedTasksCompleted() {
        List<CentralWorkflowTask> tasks = CentralWorkflowTask.findAllByTypeAndStatus(CentralWorkflowTaskType.HUMAN, WorkflowTaskStatus.COMPLETE, [fetch: [permittedSlids: 'eager', securityRoles: 'eager']])
        List<CentralWorkflowTask> permittedTasksList = []
        tasks.each { CentralWorkflowTask task ->
            if (hasPermissionForCompletedTasks(task)) {
                permittedTasksList.add(task)
            }
        }
        return permittedTasksList
    }

    static List<CentralWorkflowTask> getPermittedTasksNew() {
        String slid = SessionUtils.getSession()?.loggedUser
        if (!slid) return []

        Collection roles = SessionUtils.session?.roles
        if (!roles) roles = ['DEFAULT']

        List<CentralWorkflowTask> tasks = CentralWorkflowTask.findAll("from CentralWorkflowTask as task left outer join task.permittedSlids as permitted left outer join task.securityRoles as securityRole where task.type = ? and task.status <> ? and ((permitted.slid = ? and permitted.isArchived = false) or securityRole.id in (?))", [CentralWorkflowTaskType.HUMAN, WorkflowTaskStatus.PENDING, slid, roles.join('\',\'')])
        return tasks
    }

    static Boolean hasPermission(String slid = null, CentralWorkflowTask task) {
        slid = slid ?: SessionUtils.getSession()?.loggedUser
        if (!slid) {
            return false
        }
        if (task.permittedSlids.find { it.slid == slid && !it.isArchived }) {
            return true
        }
        Collection userSecurityRoles = SessionUtils.session?.roles
        if (userSecurityRoles?.any { it in task.securityRoles } && (task.status == WorkflowTaskStatus.NEW)) {
            return true
        }
        return false
    }

    static Boolean hasPermissionForCompletedTasks(String slid = null, CentralWorkflowTask task) {
        slid = slid ?: SessionUtils.getSession()?.loggedUser
        if (!slid) {
            return false
        }
        if (task.permittedSlids.find { it.slid == slid && !it.isArchived }) {
            return true
        }
        Collection userSecurityRoles = SessionUtils.session?.roles
        if (userSecurityRoles?.any { it in task.securityRoles } && (task.status == WorkflowTaskStatus.COMPLETE)) {
            return true
        }
        return false
    }

    String getWorkflowFilePath() {
        return workflowType.workflowFilePath
    }

    String getWorkflowProcessId() {
        return workflowType.workflowProcessId
    }

    void createEscalationLogTask() {
        CentralWorkflowTask escalationLogTask = new CentralWorkflowTask();
        escalationLogTask.with {
            workflowGuid = this.workflowGuid;
            nodeName = this.nodeName
            nodeId = this.nodeId
            workItemId = this.workItemId
            actorSlid = CareConstants.CENTRAL_SYSTEM_USER_ID
            droolsSessionId = this.droolsSessionId
            response = "ESCALATED"
            message = "Escalating"
            status = WorkflowTaskStatus.COMPLETE
            type = CentralWorkflowTaskType.SYSTEM_CENTRAL
            workflowType = this.workflowType
        }
        escalationLogTask.s();
    }

    static getInitialTask(String workflowGuid) {
        return CentralWorkflowTask.findByWorkflowGuid(workflowGuid)
    }

    public static String serializeResponseEmplements(responseElements) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream()
        XMLEncoder xmlEncoder = new XMLEncoder(bos)
        xmlEncoder.writeObject(responseElements)
        xmlEncoder.flush()
        return bos.toString()
    }

    boolean hasAnyPendingTasks() {
        List<CentralWorkflowTask> tasks = CentralWorkflowTask.findAllByWorkflowGuid(workflowGuid)
        return (tasks.any { it.status in [WorkflowTaskStatus.NEW, WorkflowTaskStatus.PENDING] })
    }

    public String getAbbreviatedCodeForGroupResponse() {
        String abbreviatedCode = ""
        List<String> tokenizedWorkflowType = workflowType.name.tokenize(' ')
        tokenizedWorkflowType.each { String s ->
            abbreviatedCode = abbreviatedCode + s.substring(0, 1)
        }
        List<String> tokenizedCurrentNode = nodeName.tokenize(' ')
        tokenizedCurrentNode.each { String s ->
            abbreviatedCode = abbreviatedCode + s.substring(0, 1)
        }
        return abbreviatedCode
    }

    public boolean isAccessRequestedTask() {
        return nodeName in ['Access Granted By Feed', 'Pending APS Approval']
    }

    public boolean isAccessRevokedTask() {
        return nodeName in ['Access Revoked By Feed', 'Pending Revocation by APS']
    }
}
