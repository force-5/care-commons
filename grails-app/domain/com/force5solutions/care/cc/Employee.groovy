package com.force5solutions.care.cc

class Employee extends Worker {

    static embeddedObjects = ['person']
    static attachedObjects = ['certifications', 'entitlementRoles']

    Date dateCreated
    Date lastUpdated

    Boolean isApproved = true
    Person person
    String title
    String department
    EmployeeSupervisor supervisor
    String workerNumber
    String badgeNumber
    Set<WorkerCourse> courses = []
    Set<WorkerCertification> certifications = []
    Set<BusinessUnitRequester> businessUnitRequesters = []
    Set<WorkerEntitlementRole> entitlementRoles = []
    TerminateForCause terminateForCause
    static hasMany = [businessUnitRequesters: BusinessUnitRequester, certifications: WorkerCertification, entitlementRoles: WorkerEntitlementRole, courses: WorkerCourse]

    static mapping = {
        columns {
            supervisor column: 'employee_supervisor_id'
        }
    }

    static transients = ['supervisorName']

    String toString() {
        person.toString()
    }

    def getName() {
        person.toString()
    }

    static constraints = {
        title(nullable: true, blank: true)
        department(nullable: true, blank: true)
        supervisor(nullable: true)
        workerNumber(nullable: true)
        badgeNumber(nullable: true)
        terminateForCause(nullable: true)
    }

    String toDetailString() {
        businessUnitRequesters?.toList()?.sort {it.toString()} + workerNumber + workerImage?.size + slid + badgeNumber + supervisor?.toString() + person?.firstName + person?.lastName + person?.middleName + person?.email + person?.phone + person?.notes
    }

    public static def findBySlid(String slid) {
        return (slid ? (Employee.createCriteria().get {person { eq('slid', slid) }}) : null)
    }

    public static def countBySlid(String slid) {
        return (slid ? (Employee.createCriteria().count {person { eq('slid', slid) }}) : null)
    }

    public String getSupervisorName() {
        if (supervisor) {
            return supervisor.name
        } else {
            ""
        }
    }

    boolean equals(o) {
        if (this.is(o)) return true;
        if (!(o instanceof Employee)) return false;
        Employee employee = (Employee) o;
        return (this.ident() == employee.ident())
    }
}
