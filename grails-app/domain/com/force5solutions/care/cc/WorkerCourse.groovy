package com.force5solutions.care.cc

class WorkerCourse  {
    static belongsTo = [worker: Worker]
    Date dateCreated
    Date lastUpdated

    Boolean isApproved = true
    Course course
    Date dateCompleted

    WorkerCourse(){}

    WorkerCourse(Worker worker, Course course, Date dateCompleted = new Date()){
        this.worker = worker
        this.course = course
        this.dateCompleted = dateCompleted
    }

    String toString() {
        course.toString()
    }

    String toDetailString() {
        return "${course} ${dateCompleted?.format('MM/dd/yy')}"
    }

    static constraints = {
        dateCompleted(nullable: true)
    }

    boolean equals(o) {
        if (this.is(o)) return true;
        if (!(o.instanceOf(WorkerCourse.class))) return false;
        WorkerCourse workerCourse = (WorkerCourse) o;
        return (this.ident() == workerCourse.ident())
    }

}
