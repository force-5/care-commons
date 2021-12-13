package com.force5solutions.care.common

public enum EntitlementStatus {

    ACTIVE("Active"),
    INACTIVE("Inactive"),
    RETIRED("Retired")

    private final String name

    EntitlementStatus(String name) {
        this.name = name
    }

    String toString() {
        return name
    }


}