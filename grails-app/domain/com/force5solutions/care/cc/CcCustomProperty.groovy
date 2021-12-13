package com.force5solutions.care.cc

import com.force5solutions.care.common.CustomPropertyType

class CcCustomProperty {

    Date dateCreated
    Date lastUpdated
    String name
    static belongsTo = [entitlementPolicy:EntitlementPolicy]
    CustomPropertyType propertyType;
    Boolean isRequired
    String size
    
    static constraints = {
        size(nullable:true, blank:true)
    }

    String toString(){
        return "${name} (${propertyType}, ${((isRequired)? 'Mandatory': 'Optional')})"
    }

}
