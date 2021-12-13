package com.force5solutions.care.cc

import org.codehaus.groovy.grails.commons.ConfigurationHolder
import org.springframework.web.context.request.RequestContextHolder

public class EmployeeSupervisor {
    Date dateCreated
    Date lastUpdated

    Person person

    static config = ConfigurationHolder.config

    static transients = ['activeWorkersGroupByEntitlementRole']

    def beforeInsert = {
        person.email = AppUtil.getEmailFromSlid(person.slid)
    }

    def beforeUpdate = {
        person.email = AppUtil.getEmailFromSlid(person.slid)
    }


    public static def findBySlid(String slid) {
        return (slid ? (EmployeeSupervisor.createCriteria().get {person { eq('slid', slid) }}) : null)
    }

    public static def countBySlid(String slid) {
        return (slid ? (EmployeeSupervisor.createCriteria().count {person { eq('slid', slid) }}) : null)
    }

    static List<Employee> getSubordinates(String slid) {
        EmployeeSupervisor supervisor = EmployeeSupervisor.findBySlid(slid)
        List<Employee> employees = supervisor ? Employee.findAllBySupervisor(supervisor) : []
        return employees
    }

    public static List<Employee> getInheritedSubordinates(String slid) {
        List<Employee> employees;
        List<EmployeeSupervisor> supervisors;

        if (!supervisors) {
            supervisors = EmployeeSupervisor.list(fetch: [person: "eager"])
        }
        if (!employees) {
            employees = Employee.list(fetch: [person: "eager", supervisor: "eager"])
        }

        return _getInheritedSubordinates(slid, [] as Set, supervisors, employees)
    }

    private static List<Employee> _getInheritedSubordinates(String slid, Set<String> traversedSlids = [], List<EmployeeSupervisor> supervisors = [], List<Employee> employees = []) {
        List<Employee> inheritedSubordinateList = []
        traversedSlids.add(slid)
        EmployeeSupervisor employeeSupervisor = supervisors.find {it.slid == slid}
        if (employeeSupervisor) {
            inheritedSubordinateList.addAll(employees.findAll { Employee e ->
                e.supervisor == employeeSupervisor
            })
            inheritedSubordinateList = inheritedSubordinateList.findAll {!(it.slid in traversedSlids)}
            List subSubordinates = []
            inheritedSubordinateList.each { Employee subordinate ->
                subSubordinates.addAll(_getInheritedSubordinates(subordinate.slid as String, traversedSlids, supervisors, employees))
            }
            inheritedSubordinateList.addAll(subSubordinates)
        }
        return (inheritedSubordinateList?.flatten()?.unique()?.findAll {it})
    }

    String toString() {
        person.toString()
    }

    def getName() {
        person.toString()
    }

    public def getFirstName() {
        person?.firstName
    }

    public def getLastName() {
        person?.lastName
    }

    public def getMiddleName() {
        person?.middleName
    }

    public def getNotes() {
        person?.notes
    }

    public def getPhone() {
        person?.phone
    }

    public def getFirstMiddleLastName() {
        person?.firstMiddleLastName
    }

    public def getSlid() {
        person?.slid
    }

    public def getEmail() {
        person?.email
    }

    public Map<CcEntitlementRole, List<Worker>> getActiveWorkersGroupByEntitlementRole() {
        Map activeRolesMap = [:]
        EmployeeSupervisor.withSession {
            List activeWorkerEntitlementRoles = WorkerEntitlementRole.findAllByStatus(EntitlementRoleAccessStatus.ACTIVE)
            activeWorkerEntitlementRoles = activeWorkerEntitlementRoles?.findAll {it.worker.supervisor == this}

            Map activeWorkerEntitlementRolesByER = activeWorkerEntitlementRoles.groupBy {it.entitlementRole}
            activeWorkerEntitlementRolesByER.each {CcEntitlementRole entitlementRole, List<WorkerEntitlementRole> workerEntitlementRoles ->
                activeRolesMap.put(entitlementRole, workerEntitlementRoles*.worker)
            }
        }

        return activeRolesMap
    }

    public List<WorkerEntitlementRole> getActiveWorkerEntitlementRolesForSubordinates() {
        List<WorkerEntitlementRole> activeWorkerEntitlementRolesUnderSupervisor = []
        EmployeeSupervisor.withSession {
            List activeWorkerEntitlementRoles = WorkerEntitlementRole.findAllByStatus(EntitlementRoleAccessStatus.ACTIVE)
            activeWorkerEntitlementRolesUnderSupervisor = activeWorkerEntitlementRoles?.findAll {it.worker.supervisor == this}
        }
        return activeWorkerEntitlementRolesUnderSupervisor
    }

    boolean equals(o) {
        if (this.is(o)) return true;
        if (!(o.instanceOf(EmployeeSupervisor.class))) return false;
        EmployeeSupervisor employeeSupervisor = (EmployeeSupervisor) o;
        return (this.ident() == employeeSupervisor.ident())
    }
}