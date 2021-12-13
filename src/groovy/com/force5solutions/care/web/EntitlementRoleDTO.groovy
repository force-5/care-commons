package com.force5solutions.care.web

class EntitlementRoleDTO {

    String id
    String name
    String status
    String notes
    String standards
    String types
    String gatekeepers
    String tags

    String toString() {
        return name
    }
}
