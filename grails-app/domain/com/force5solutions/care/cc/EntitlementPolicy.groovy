package com.force5solutions.care.cc

import org.codehaus.groovy.grails.commons.ApplicationHolder

class EntitlementPolicy {

    static requireApprovalObjects = ['standards', 'requiredCertifications', 'requiredCertificationsForEmployee', 'requiredCertificationsForContractor']

    String name
    Boolean isApproved = false
    List<com.force5solutions.care.cc.CcCustomProperty> customProperties = []
    List<String> standards = []
    Set<Certification> requiredCertifications = []
    Set<Certification> requiredCertificationsForEmployee = []
    Set<Certification> requiredCertificationsForContractor = []
    Date dateCreated
    Date lastUpdated

    static hasMany = [customProperties: com.force5solutions.care.cc.CcCustomProperty, standards: String, requiredCertifications: Certification, requiredCertificationsForEmployee: Certification, requiredCertificationsForContractor: Certification]

    String toString() {
        return name
    }

    static List<EntitlementPolicy> listApproved() {
        return EntitlementPolicy.findAllByIsApproved(true)
    }

    static constraints = {
        name(unique: true)
        requiredCertifications(nullable: true)
        requiredCertificationsForEmployee(nullable: true)
        requiredCertificationsForContractor(nullable: true)
        dateCreated(nullable: true)
        lastUpdated(nullable: true)
        customProperties(nullable: true)
    }

    static mapping = {
        requiredCertifications lazy: false
        requiredCertificationsForEmployee lazy: false
        requiredCertificationsForContractor lazy: false
        standards lazy: false
        customProperties lazy: false
    }

    boolean equals(o) {
        if (this.is(o)) return true;
        if (!(o.instanceOf(EntitlementPolicy.class))) return false;
        EntitlementPolicy pt = (EntitlementPolicy) o;
        return (this.ident() == pt.ident())
    }

    int hashCode() {
        return this.ident().toInteger()
    }

    static void activateEntitlementPolicy(Long entitlementPolicyId) {
        EntitlementPolicy entitlementPolicy = EntitlementPolicy.get(entitlementPolicyId)
        entitlementPolicy.isApproved = true
        def entitlementPolicyService = ApplicationHolder.getApplication().getMainContext().getBean('entitlementPolicyService')
        entitlementPolicyService.save(entitlementPolicy)
    }

    static void updateEntitlementPolicy(Long entitlementPolicyId) {
        EntitlementPolicy entitlementPolicy = EntitlementPolicy.get(entitlementPolicyId)
        entitlementPolicy.isApproved = true
        def versioningService = ApplicationHolder.getApplication().getMainContext().getBean('versioningService')
        versioningService.approvePendingChanges(entitlementPolicy)
    }
}
