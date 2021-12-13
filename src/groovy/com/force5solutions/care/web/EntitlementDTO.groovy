package com.force5solutions.care.web

class EntitlementDTO {

    String id
    String alias
    String status
    String notes
    String type
    String origin

    String toString() {
        return alias
    }
}
