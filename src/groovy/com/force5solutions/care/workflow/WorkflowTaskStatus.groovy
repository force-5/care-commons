package com.force5solutions.care.workflow

public enum WorkflowTaskStatus {
    NEW('NEW'),
    PENDING('PENDING'),
    COMPLETE('COMPLETE'),
    CANCELLED('CANCELLED')

    private final String name

    WorkflowTaskStatus(String name) {
        this.name = name
    }

    String toString() {
        return name
    }
}