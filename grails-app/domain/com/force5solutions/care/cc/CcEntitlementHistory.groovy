package com.force5solutions.care.cc

public class CcEntitlementHistory {
    String entitlementId

    static mapping = {
        tablePerHierarchy false
        sort(dateCreated: 'desc')
    }

}