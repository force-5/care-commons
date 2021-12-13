package com.force5solutions.care.cc

enum CentralApplicationRole {

    VENDOR('Vendor'),
    WORKER('Worker'),
    SUPERVISOR('Supervisor'),
    BUSINESS_UNIT_REQUESTER('Business Unit Requester'),
    SUBORDINATES('Subordinates')

    private final String name;

    CentralApplicationRole(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }

    static list() {
        [VENDOR, WORKER, SUPERVISOR, BUSINESS_UNIT_REQUESTER, SUBORDINATES]
    }

    static CentralApplicationRole get(String name) {
        return (CentralApplicationRole.list().find {it.name == name})
    }

    public String getKey() {
        return name()
    }


}
