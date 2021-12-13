package com.force5solutions.care.feed

public enum FeedOperation {

    CREATE('Create', 'Records Created'),
    UPDATE('Update', 'Records Updated'),
    DELETE('Delete', 'Records Deleted'),
    PROCESS('Process', 'Records Processed')

    private final String name
    public final String message

    FeedOperation(String name, message) {
        this.name = name
        this.message = message
    }

    public String toString() {
        return name
    }


}