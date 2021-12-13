package com.force5solutions.care.feed

class WorkerCourseInfo {
    String employeeSlid
    String courseNumber
    String courseName
    Date completionDate

    WorkerCourseInfo() {}

    public String getEmployeeSlid() {
        return (employeeSlid?.toUpperCase())
    }

    WorkerCourseInfo(String employeeSlid, String courseNumber, String courseName, Date completionDate) {
        this.employeeSlid = employeeSlid?.toUpperCase()
        this.courseNumber = courseNumber
        this.courseName = courseName
        this.completionDate = completionDate
        this.completionDate?.clearTime()
    }

    String toString() {
        return "${employeeSlid} : ${courseNumber} : ${courseName} : ${completionDate?.myFormat()}"
    }

    static constraints = {
        employeeSlid(nullable: true)
        courseNumber(nullable: true)
        courseName(nullable: true)
        completionDate(nullable: true)
    }
}
