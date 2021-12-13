package com.force5solutions.care.cc

import static com.force5solutions.care.common.CareConstants.*
import com.force5solutions.care.common.SessionUtils
import org.codehaus.groovy.grails.commons.ApplicationHolder

class WorkerEntitlementRole  {
    Date dateCreated
    Date lastUpdated

    Boolean isApproved = true
    Date lastStatusChange
    CcEntitlementRole entitlementRole
    EntitlementRoleAccessStatus status
    Boolean wasEverActive = false
    def sessionFactory
    String currentNode
    String customPropertyValues

    static belongsTo = [worker: Worker]

    static constraints = {
        status(nullable: true)
        currentNode(nullable: true)
        customPropertyValues(nullable: true)
    }

    def beforeUpdate = {
        EntitlementRoleAccessStatus pendingStatus = EntitlementRoleAccessStatus.pendingApproval
        if (status != pendingStatus) {
            currentNode = null
        }
    }

    static transients = ["nextPossibleStatuses", "rolesForCurrentWorkerEntitlementRole", "pendingCertifications"]

    String toString() {
        entitlementRole.toString()
    }

    String toDetailString() {
        status
    }

    Set<Certification> getMissingCertifications() {
        Set<Certification> requiredCertifications = (entitlementRole.requiredCertifications + entitlementRole.getInheritedCertifications(worker))?.findAll {it}
        Set<Certification> workerCertifications = (worker.effectiveCertifications.findAll {new Date() < it.expiry})*.certification as Set
        Set<Certification> missingCertifications = requiredCertifications.findAll {!(it.id in workerCertifications*.id)}
        return missingCertifications
    }

    static Boolean isMissingCertifications(Long workerEntitlementRoleId) {
        WorkerEntitlementRole workerEntitlementRole = WorkerEntitlementRole?.get(workerEntitlementRoleId)
        return (workerEntitlementRole.missingCertifications.size() != 0)
    }

    Boolean isPendingCertifications() {
        return (isMissingCertifications(this.id))

    }

    Set<Certification> getPendingCertifications() {
        return missingCertifications
    }

    List<CentralApplicationRole> getApplicationRoles(String loggedInUserSlid) {
        List<CentralApplicationRole> applicableRoles = []

        if (loggedInUserSlid in worker.businessUnitRequesters*.slid) {
            applicableRoles.add(CentralApplicationRole.BUSINESS_UNIT_REQUESTER)
        }

        if ((worker.instanceOf(Employee.class)) && (loggedInUserSlid == worker?.supervisor?.slid)) {
            applicableRoles.add(CentralApplicationRole.SUPERVISOR)
        }

        if (loggedInUserSlid == worker.slid) {
            applicableRoles.add(CentralApplicationRole.WORKER)
        }

        return applicableRoles
    }

    def canDelete() {
        return !(wasEverActive || (status in [EntitlementRoleAccessStatus.terminatedForCause, EntitlementRoleAccessStatus.pendingTermination]))
    }

    static boolean isSponsor(def workerEntitlementRoleId) {
        String loggedInUserSlid = SessionUtils.getSession()?.loggedUser ?: CENTRAL_SYSTEM_USER_ID
        WorkerEntitlementRole workerEntitlementRole = WorkerEntitlementRole.get(workerEntitlementRoleId)
        Worker worker = workerEntitlementRole.worker
        return (loggedInUserSlid in (worker.isContractor() ? worker.businessUnitRequesters*.slid : [worker.supervisor?.slid]))
    }

    static void changeWorkerEntitlementRoleStatus(def workerEntitlementRoleId, EntitlementRoleAccessStatus status, Map responseElements, String currentNode = null) {
        def workerEntitlementRoleService = ApplicationHolder.getApplication().getMainContext().getBean('workerEntitlementRoleService')
        String loggedInUserSlid = SessionUtils.getSession()?.loggedUser ?: CENTRAL_SYSTEM_USER_ID
        workerEntitlementRoleService.changeEntitlementRoleStatus(workerEntitlementRoleId, status, loggedInUserSlid, currentNode, responseElements?.toString())
    }
}