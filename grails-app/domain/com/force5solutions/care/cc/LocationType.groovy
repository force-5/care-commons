package com.force5solutions.care.cc

class LocationType {
    Date dateCreated
    Date lastUpdated

    String type
    Integer level
    Boolean isSelectable = false
    Boolean isEditable = true
    LocationType parent

    static constraints = {
        type(unique: true)
    }

    String toString(){
        type
    }
}