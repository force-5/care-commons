package com.force5solutions.care.cc

import com.force5solutions.care.web.EntitlementRoleDTO
import com.force5solutions.care.common.EntitlementStatus
import com.force5solutions.care.common.CareConstants
import org.codehaus.groovy.grails.commons.ConfigurationHolder

class CcEntitlementRole {

    // wokrs.
    String id
    String name
    EntitlementStatus status = EntitlementStatus.INACTIVE
    CcOrigin origin
    String notes
    Location location
    String standards
    String types
    Set<CcEntitlement> entitlements = []
    Set<CcEntitlementRole> entitlementRoles = []
    Set<Certification> requiredCertifications = []
    String gatekeepers
    String tags
    Boolean deletedFromAps = false

    static belongsTo = [location: Location]

    static hasMany = [entitlements: CcEntitlement, entitlementRoles: CcEntitlementRole, requiredCertifications: Certification]

    static transients = ['cip']

    CcEntitlementRole(EntitlementRoleDTO entitlementRoleDTO) {
        this.id = entitlementRoleDTO.id
        this.notes = entitlementRoleDTO.notes
        this.name = entitlementRoleDTO.name
        this.standards = entitlementRoleDTO.standards
        this.types = entitlementRoleDTO.types
        this.gatekeepers = entitlementRoleDTO.gatekeepers
//        this.status = EntitlementStatus."${entitlementRoleDTO.status}"
        this.status = EntitlementStatus.ACTIVE
        this.tags = entitlementRoleDTO.tags ?: CareConstants.DEFAULT_TAG_FOR_ENTITLEMENT_ROLE
        this.location = Location.list().find {it.isBusinessUnit()}
        this.s()
    }

    public static List<CcEntitlementRole> listUndeleted() {
        return findAllByDeletedFromAps(false)
    }

    static CcEntitlementRole upsert(EntitlementRoleDTO entitlementRoleDTO) {
        CcEntitlementRole entitlementRole
        if (countById(entitlementRoleDTO.id)) {
            entitlementRole = findById(entitlementRoleDTO.id)
            entitlementRole.notes = entitlementRoleDTO.notes
            entitlementRole.name = entitlementRoleDTO.name
            entitlementRole.standards = entitlementRoleDTO.standards
            entitlementRole.types = entitlementRoleDTO.types
            entitlementRole.gatekeepers = entitlementRoleDTO.gatekeepers
//            entitlementRole.status = EntitlementStatus."${entitlementRoleDTO.status}"
            entitlementRole.status = EntitlementStatus.ACTIVE
            entitlementRole.tags = entitlementRoleDTO.tags ?: CareConstants.DEFAULT_TAG_FOR_ENTITLEMENT_ROLE
            entitlementRole.s()
        } else {
            entitlementRole = new CcEntitlementRole(entitlementRoleDTO)
        }
        return entitlementRole
    }

    String toDetailString() {
        name + (requiredCertifications*.periodUnit)?.sort()
    }

    String toTreeString() {
        return "/${location.parent.name}/${location.name}/${name}"
    }

    Set<Certification> inheritedCertifications = []

    Set<Certification> getInheritedCertifications(Worker worker) {
        Set<Certification> certifications = []
        certifications.addAll(getInheritedCertificationsFromLocationsAndIncludedEntitlementRoles().flatten())
        certifications.addAll(getInheritedEntitlementPolicies()*.requiredCertifications.flatten())
        if (worker instanceof Employee) {
            certifications.addAll(getInheritedEntitlementPolicies()*.requiredCertificationsForEmployee.flatten())
        } else {
            certifications.addAll(getInheritedEntitlementPolicies()*.requiredCertificationsForContractor.flatten())
        }
        return certifications.flatten()
    }

    Set<Certification> getInheritedCertificationsFromLocationsAndIncludedEntitlementRoles() {
        Set<Certification> certifications = []
        Location currentLocation = location
        if (currentLocation.requiredCertifications) {
            certifications.addAll(currentLocation.requiredCertifications)
        }
        while (currentLocation.parent) {
            Location temp = currentLocation.parent
            if (temp.requiredCertifications) {
                certifications.addAll(temp.requiredCertifications)
            }
            currentLocation = temp
        }
        entitlementRoles.each {CcEntitlementRole entitlementRole ->
            if (entitlementRole.requiredCertifications) {
                certifications.addAll(entitlementRole.requiredCertifications)
            }
            if (entitlementRole.inheritedCertifications) {
                certifications.addAll(entitlementRole.inheritedCertifications)
            }
        }
        return certifications
    }

    static mapping = {
        id generator: 'assigned'
        gatekeepers type: 'text'
    }

    static constraints = {
        notes(maxSize: 5000, nullable: true, blank: true)
        origin(nullable: true, blank: true)
        standards(nullable: true, blank: true)
        types(nullable: true, blank: true)
        tags(nullable: true, blank: true)
    }

    String toString() {
        return name
    }

    CcEntitlementRole() {
    }

    boolean equals(o) {
        if (this.is(o)) return true;
        if (!(o.instanceOf(CcEntitlementRole.class))) return false;
        CcEntitlementRole pg = (CcEntitlementRole) o;
        return (this.ident() == pg.ident())
    }

    Set<String> getInheritedStandards() {
        Set<String> inheritedStandards = []
        entitlements?.each {CcEntitlement entitlement ->
            entitlement.type.standards.each {
                inheritedStandards.add(it)
            }
        }
        entitlementRoles.each {CcEntitlementRole entitlementRole ->
            entitlementRole.entitlements?.each {CcEntitlement entitlement ->
                entitlement.type?.standards?.each {
                    inheritedStandards.add(it)
                }
            }
        }
        return inheritedStandards
    }

    Set<EntitlementPolicy> getInheritedEntitlementPolicies() {
        Set<EntitlementPolicy> inheritedEntitlementPolicies = []
        Set<String> entitlementPolicies = types?.tokenize(',') as Set
        entitlementPolicies.each {
            inheritedEntitlementPolicies.add(EntitlementPolicy.findByName(it.trim()))
        }
        return inheritedEntitlementPolicies
    }

    boolean isCip() {
        List<String> cipEntitlementPolicies = ConfigurationHolder?.config?.cipEntitlementPolicies?.toString()?.tokenize(',')*.trim()
        List<String> inheritedPoliciesName = (getInheritedEntitlementPolicies() as List)*.name
        return cipEntitlementPolicies && inheritedPoliciesName.intersect(cipEntitlementPolicies)
    }
}
