package com.force5solutions.care.cc

class CcCustomPropertyValue {

    Date dateCreated
    Date lastUpdated
    CcCustomProperty customProperty

    String value
    static constraints = {
    }

    String toString(){
        return "${customProperty.name} : ${value}"
    }

}
