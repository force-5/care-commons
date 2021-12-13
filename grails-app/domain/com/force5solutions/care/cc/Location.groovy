package com.force5solutions.care.cc

import static com.force5solutions.care.common.CareConstants.*

class Location {
    Date dateCreated
    Date lastUpdated

    String name
    Location parent
    LocationType locationType
    Set<Certification> requiredCertifications = []
    Set<Certification> sponsorCertifications = []
    Set<Location> childLocations = []
    Set<CcEntitlementRole> entitlementRoles = []
    String notes

    static transients = ['businessUnit']
    static hasMany = [entitlementRoles: CcEntitlementRole, requiredCertifications: Certification, sponsorCertifications: Certification, childLocations: Location]

    static constraints = {
        locationType()
        parent(nullable: true)
        notes(nullable: true)
    }

    String toDetailString() {
        name + (requiredCertifications*.periodUnit)?.sort() + (inheritedCertifications*.periodUnit)?.sort() + parent?.name
    }

    String toTreeString() {
        List<String> names = [name]
        Location currentLocation = this
        while(currentLocation.parent){
            Location parent = currentLocation.parent
            names = [parent.name] + names
            currentLocation = parent
        }

        return (names.join(' > '))
//        parent?.parent?.name + "/" + parent?.name + "/" + name
    }

    Set<Certification> getInheritedCertifications() {
        Set<Certification> inheritedCertifications1 = [] as Set
        Location location = this
        while (location.parent) {
            location = location.parent
            inheritedCertifications1 += location.requiredCertifications
        }
        return inheritedCertifications1
    }

    Set<Certification> getInheritedSponsorCertifications() {
        Set<Certification> inheritedSponsorCertifications1 = [] as Set
        Location location = this
        while (location.parent) {
            location = location.parent
            inheritedSponsorCertifications1 += location.sponsorCertifications
        }
        return inheritedSponsorCertifications1
    }

    String toString() {
        name
    }

    boolean isBusinessUnit(){
        return (locationType.type == LOCATION_TYPE_BUSINESS_UNIT)
    }

    boolean equals(o) {
        if (this.is(o)) return true;
        if (!(o.instanceOf(Location.class))) return false;
        Location location = (Location) o;
        return (this.ident() == location.ident())
    }

}
