package com.force5solutions.care.cc

import org.codehaus.groovy.grails.commons.ConfigurationHolder as CH

import com.force5solutions.care.common.CareConstants
import org.codehaus.groovy.grails.commons.ConfigurationHolder

public class Worker {

    CentralDataFile workerImage
    static config = CH.config

    static constraints = {
        workerImage nullable: true
    }

    public Boolean isContractor() {
        return (this?.instanceOf(Contractor.class))
    }

    public Boolean isEmployee() {
        return (this?.instanceOf(Employee.class))
    }

    Set<Certification> getMissingCertifications() {
        Set<Certification> missingCertifications = []
        entitlementRoles?.each {WorkerEntitlementRole entitlementRole ->
            missingCertifications.addAll(entitlementRole.missingCertifications)
        }
        return missingCertifications.flatten()
    }

    def getRecentWorkerCertificationByCertificationId(Long certificationId) {
        List<WorkerCertification> workerCertifications = certifications?.findAll {it.certification.id == certificationId}?.sort {it.id}
        WorkerCertification recentCertification = (workerCertifications) ? workerCertifications?.last() : null
        return recentCertification
    }

    //Current Certifications are list latest worker certifications for each certification (include expired and not-done: which do not have dateCompleted)

    Set<WorkerCertification> getCurrentCertifications() {
        Set<WorkerCertification> workerCertifications = []
        String query = "select max(id) from WorkerCertification wc where wc.worker = :worker group by wc.certification"
        List<Long> workerCertificationIds = WorkerCertification.executeQuery(query, [worker: this])
        workerCertifications = workerCertificationIds ? WorkerCertification.getAll(workerCertificationIds) : []

        Set<WorkerCertification> effectiveCertifications = effectiveCertifications
        if (effectiveCertifications) {
            effectiveCertifications = effectiveCertifications.findAll {!it.isExpired()}
            workerCertifications = workerCertifications.findAll {!(it.certification in effectiveCertifications*.certification)}
            workerCertifications = effectiveCertifications + workerCertifications
        }
        return workerCertifications
    }

    //Effective Certifications include expiredCertifications

    Set<WorkerCertification> getEffectiveCertifications() {
        Set<WorkerCertification> workerCertifications = []
        String query = "select max(id) from WorkerCertification wc where wc.worker = :worker and wc.dateCompleted is not null group by wc.certification"
        List<Long> workerCertificationIds = WorkerCertification.executeQuery(query, [worker: this])
        workerCertifications = workerCertificationIds ? WorkerCertification.getAll(workerCertificationIds) : []
        return workerCertifications
    }

    //Latest worker Certifications which have not expired yet

    Set<WorkerCertification> getActiveCertifications() {
        Set<WorkerCertification> workerCertifications = effectiveCertifications
        workerCertifications.removeAll {it.isExpired()}
        return workerCertifications
    }

    // Returns the access type of employee on any of the active entitlements for both the 'Physical' & 'Cyber' entitlement policies. Returns "Both" OR "Physical" OR "Cyber"

    public def getAccessTypeForEmployeeBasedOnEntitlementPolicy() {
        String accessType
        String entitlementPolicyTypes
        List tokenizedList, trimmedList = []

        activeEntitlementRoles.each { WorkerEntitlementRole workerEntitlementRole ->
            entitlementPolicyTypes = entitlementPolicyTypes ? entitlementPolicyTypes + ',' + workerEntitlementRole.entitlementRole.types : workerEntitlementRole.entitlementRole.types
        }
        tokenizedList = entitlementPolicyTypes?.tokenize(',')
        tokenizedList.each { String s ->
            trimmedList << s.trim()
        }

        if ("Physical" in trimmedList && "Cyber" in trimmedList) {
            accessType = CareConstants.ACCESS_TYPE_BOTH
        } else {
            if ("Physical" in trimmedList) {
                accessType = CareConstants.ACCESS_TYPE_PHYSICAL_ONLY
            } else if ("Cyber" in trimmedList) {
                accessType = CareConstants.ACCESS_TYPE_CYBER_ONLY
            }
        }
        return accessType
    }

    // Returns the access type of employee on any of the active entitlement roles based on the roleId properties set in Config.

    public def getAccessTypeForWorkerBasedOnConfigProperties() {
        String accessType = null
        activeEntitlementRoles.each { WorkerEntitlementRole workerEntitlementRole ->
            String roleId = workerEntitlementRole.entitlementRole.id
            switch (roleId) {
                case ConfigurationHolder?.config?.physicalEntitlementRoleId?.toString():
                    accessType = CareConstants.ACCESS_TYPE_PHYSICAL_ONLY
                    break
                case ConfigurationHolder?.config?.electronicEntitlementRoleId?.toString():
                    accessType = CareConstants.ACCESS_TYPE_CYBER_ONLY
                    break
                case ConfigurationHolder?.config?.bothEntitlementRoleId?.toString():
                    accessType = CareConstants.ACCESS_TYPE_BOTH
                    break
            }
        }
        return accessType
    }

    // Returns whether employee has a praCertification as defined in the config property. If employee possess the certification, it returns "Active".

    public def getPraStatusForEmployee() {
        String praStatus
        if (certifications.size() > 0 && config.praCertificationId.toLong() in activeCertifications*.certification.id) {
            praStatus = "Active"
        } else {
            praStatus = "Inactive"
        }
        return praStatus
    }

    def getStatus() {
        if (terminateForCause) {
            return WorkerStatus.TERMINATED
        } else if (entitlementRoles?.size() > 0) {
            return (entitlementRoles.any {it.status == EntitlementRoleAccessStatus.active}) ? WorkerStatus.ACTIVE : WorkerStatus.INACTIVE
        } else {
            return WorkerStatus.UNASSIGNED
        }
    }

    def getRecentStatusChange() {
        return ((entitlementRoles) ? entitlementRoles.max {it.lastStatusChange}.lastStatusChange : null)
    }

    public def getFirstMiddleLastName() {
        person?.firstMiddleLastName
    }

    public def getFirstName() {
        person?.firstName
    }

    public def getLastName() {
        person?.lastName
    }

    public def getMiddleName() {
        person?.middleName
    }

    public def getNotes() {
        person?.notes
    }

    public def getPhone() {
        person?.phone
    }

    public def getSlid() {
        person?.slid
    }

    public def getEmail() {
        person?.email
    }

    public Set<WorkerEntitlementRole> getActiveEntitlementRoles() {
        Set<WorkerEntitlementRole> activeRoles = entitlementRoles.findAll { role -> (role.status == EntitlementRoleAccessStatus.active) }
        return activeRoles
    }

    public boolean hasActiveEntitlementRole() {
        return activeEntitlementRoles.size() ? true : false
    }

    public def getBusinessUnitRequester() {
        return businessUnitRequesters ? businessUnitRequesters?.toList()?.sort {it?.lastName}?.first() : null
    }

    public def getSponsorName() {
        if (isContractor()) {
            BusinessUnitRequester bur = getBusinessUnitRequester();
            return bur.person.getFirstMiddleLastName()
        }
        if (isEmployee() && (supervisor)) {
            return supervisor.person.getFirstMiddleLastName()

        }
    }

    public String toString() {
        lastName + ", " + firstName + (middleName ?: "")
    }
}