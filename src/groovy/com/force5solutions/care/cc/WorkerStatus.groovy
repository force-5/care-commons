package com.force5solutions.care.cc

enum WorkerStatus {

    ACTIVE('Active'),
    INACTIVE('Inactive'),
    TERMINATED('Terminated'),
    UNASSIGNED('Unassigned')

    final String name;

    WorkerStatus(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }

    String getKey() {
        return name()
    }

    static list() {
        return [ACTIVE, INACTIVE, TERMINATED, UNASSIGNED]
    }

    static WorkerStatus getWorkerStatus(String s) {
        switch (s) {
            case 'Active': return WorkerStatus.ACTIVE; break;
            case 'Inactive': return WorkerStatus.INACTIVE; break;
            case 'Terminated': return WorkerStatus.TERMINATED; break;
            case 'Unassigned': return WorkerStatus.UNASSIGNED; break;
        }
        return null
    }
}