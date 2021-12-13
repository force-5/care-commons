package com.force5solutions.care.workflow

public enum CentralWorkflowTaskType {
    SYSTEM_CENTRAL('SYSTEM_CENTRAL'),
    SYSTEM_APS('SYSTEM_APS'),
    HUMAN('HUMAN')

    private final String name

    CentralWorkflowTaskType(String name) {
        this.name = name
    }

    String toString() {
        return name
    }

    static list(){
        [SYSTEM_CENTRAL, SYSTEM_APS, HUMAN]
    }


    static CentralWorkflowTaskType get(String name){
        return (CentralWorkflowTaskType.list().find{it.name == name})
    }


     String getKey() {
        return name()
    }
}