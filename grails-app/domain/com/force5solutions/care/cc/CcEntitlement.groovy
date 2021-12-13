package com.force5solutions.care.cc

import com.force5solutions.care.common.EntitlementStatus
import com.force5solutions.care.web.EntitlementDTO

class CcEntitlement {

    String id
    String alias
    EntitlementStatus status = EntitlementStatus.INACTIVE
    EntitlementPolicy type
    CcOrigin origin
    String notes
    List<CcCustomPropertyValue> customPropertyValues = []
    static hasMany = [customPropertyValues: CcCustomPropertyValue]

    static constraints = {
        alias(unique: true, nullable: true, blank: true)
        notes(maxSize: 5000, nullable: true, blank: true)
        origin(nullable: true, blank: true)
    }

    static mapping = {
        id generator: 'assigned'
    }

    CcEntitlement(EntitlementDTO entitlementDTO) {
        this.id = entitlementDTO.id
        this.notes = entitlementDTO.notes
        this.alias = entitlementDTO.alias
        this.type = EntitlementPolicy.get(entitlementDTO.type.toLong())
        this.origin = CcOrigin.findByName(entitlementDTO.origin)
        this.status = EntitlementStatus.INACTIVE
        this.s()

    }

    CcEntitlement() {
        }

    static CcEntitlement upsert(EntitlementDTO entitlementDTO) {
        CcEntitlement entitlement
        if (CcEntitlement.countById(entitlementDTO.id)) {
            entitlement = CcEntitlement.findById(entitlementDTO.id)
            entitlement.notes = entitlementDTO.notes
            entitlement.alias = entitlementDTO.alias
            entitlement.type = EntitlementPolicy.get(entitlementDTO.type.toLong())
            entitlement.origin = CcOrigin.findByName(entitlementDTO.origin)
            entitlement.status = EntitlementStatus.INACTIVE
            entitlement.s()
        } else {
            entitlement = new CcEntitlement(entitlementDTO)
        }
        return entitlement
    }


    String toString(){
        return alias
    }
    boolean equals(o) {
        if (this.is(o)) return true;
        if (!(o.instanceOf(CcEntitlement.class))) return false;
        CcEntitlement p = (CcEntitlement) o;
        return (this.ident() == p.ident())
    }


}
