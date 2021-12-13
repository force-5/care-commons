package com.force5solutions.care.cc

public enum TrainingTestStatus {
    COMPLETED("Completed"),
    PENDING("Pending"),
    NOT_STARTED("Not Started")

    private final String name;

    TrainingTestStatus(String name) {
        this.name = name;
    }


    public String toString() {
        return name;
    }

    public static TrainingTestStatus values(Integer i){
        switch(i){
            case 0: return TrainingTestStatus.COMPLETED
            case 1: return TrainingTestStatus.PENDING
            case 2: return TrainingTestStatus.NOT_STARTED
        }
    }
}