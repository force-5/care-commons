package com.force5solutions.care.workflow

public enum ApsWorkflowTaskType {
    SYSTEM_TIM('SYSTEM_TIM'),
    SYSTEM_APS('SYSTEM_APS'),
    HUMAN('HUMAN')

    private final String name

    ApsWorkflowTaskType(String name) {
        this.name = name
    }

    String toString() {
        return name
    }

    String getKey() {
        return name()
    }
}