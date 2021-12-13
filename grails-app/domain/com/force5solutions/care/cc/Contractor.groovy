package com.force5solutions.care.cc

import java.text.DecimalFormat

import com.force5solutions.care.common.SessionUtils

class Contractor extends Worker {

    static embeddedObjects = ['person']
    static attachedObjects = ['certifications', 'entitlementRoles']

    Date dateCreated
    Date lastUpdated

    Boolean isApproved = true
    Person person
    Integer birthDay
    Integer birthMonth
    Vendor primeVendor
    ContractorSupervisor supervisor
    Vendor subVendor
    ContractorSupervisor subSupervisor
    String workerNumber
    String formOfId // driving license (It could be an enum as well)
    String badgeNumber
    Set<WorkerCourse> courses = []
    Set<BusinessUnitRequester> businessUnitRequesters = []
    Set<WorkerCertification> certifications = []
    Set<WorkerEntitlementRole> entitlementRoles = []
    TerminateForCause terminateForCause
    static hasMany = [businessUnitRequesters: BusinessUnitRequester, certifications: WorkerCertification, entitlementRoles: WorkerEntitlementRole, courses: WorkerCourse]

    // Default contructor

    static transients = ['supervisorName']

    Contractor() {
    }

    // Construct the object from the command object.

    public static Contractor createContractorObject(def contractorCO) {
        Contractor contractorInstance;
        contractorCO.with {
            if (id) {
                contractorInstance = Contractor.findById(id)
            } else {
                contractorInstance = new Contractor()
                contractorInstance.person = new Person()
            }

            Person person = (slid) ? Person.findBySlid(slid) : null
            contractorInstance.person = person ? person : contractorInstance.person
            if (!contractorInstance.person) {
                contractorInstance.person = new Person()
            }
            contractorInstance.person.firstName = firstName
            contractorInstance.person.middleName = middleName
            contractorInstance.person.lastName = lastName
            contractorInstance.person.phone = phone
            contractorInstance.person.email = email
            contractorInstance.person.slid = (slid?.length() > 0) ? slid : null
            contractorInstance.person.notes = notes
            contractorInstance.formOfId = formOfId
            contractorInstance.birthDay = birthDay > 0 ? birthDay : null
            contractorInstance.birthMonth = birthMonth > 0 ? birthMonth : null
            contractorInstance.badgeNumber = badgeNumber
            contractorInstance.primeVendor = primeVendor ? Vendor.findById(primeVendor) : null
            contractorInstance.supervisor = supervisor ? ContractorSupervisor.findById(supervisor) : null
            contractorInstance.subVendor = subVendor ? Vendor.findById(subVendor) : null
            contractorInstance.subSupervisor = subSupervisor ? ContractorSupervisor.findById(subSupervisor) : null
            contractorInstance.businessUnitRequesters = businessUnitRequesters?.size() > 0 ? BusinessUnitRequester.getAll(businessUnitRequesters) : []
            contractorInstance.courses = []

            if (fileContent?.size() > 0) {
                contractorInstance.workerImage = new CentralDataFile()
                contractorInstance.workerImage.bytes = fileContent
                contractorInstance.workerImage.fileName = contractorInstance.name
                contractorInstance.workerImage.s()
                SessionUtils.session.isContractorImageUpdated = true
            }

            if (courses) {
                courses.each { def course ->
                    contractorInstance.addToCourses(new WorkerCourse(contractorInstance, Course.get(course)))
                }
            }
            contractorInstance.terminateForCause = terminateForCause ? TerminateForCause.findById(terminateForCause.toLong()) : null
        }
        return contractorInstance
    }


    static constraints = {
        primeVendor(nullable: true, blank: false)
        supervisor(nullable: true)
        subSupervisor(nullable: true)
        subVendor(nullable: true)
        workerNumber(nullable: true)
        formOfId(nullable: true)
        badgeNumber(nullable: true)
        birthDay(nullable: true, min: 1, max: 31)
        birthMonth(nullable: true, min: 1, max: 12)
        terminateForCause(nullable: true)
    }

    def beforeInsert = {
//        workerNumber = populateContractorNumber()
    }

    synchronized String populateContractorNumber() {
        String newContractorNumber = "c" + (primeVendor ? primeVendor.companyName?.charAt(0) : 'x') + person?.firstName?.charAt(0) + person?.lastName?.charAt(0)
        newContractorNumber = newContractorNumber.toUpperCase()
        def c = Contractor.createCriteria()
        List<Long> workerNumbers = c {
            projections {
                max('id')
            }
        }
        Long i = 1
        if (workerNumbers && workerNumbers.first()) {
            i += workerNumbers.first()
        }
        DecimalFormat df = new DecimalFormat("00000");
        return "${newContractorNumber}${df.format(i)}"
    }

    String toString() {
        person
    }

    def getName() {
        person?.lastName + (person?.lastName ? ", " : "") + person?.firstName
    }

    String toDetailString() {
        businessUnitRequesters?.toList()?.sort { it.toString() } +
                workerNumber + workerImage?.size + formOfId + slid + badgeNumber +
                primeVendor?.companyName + supervisor.toString() + subVendor?.companyName + subSupervisor.toString() + person?.firstName +
                person?.lastName + person?.middleName + person?.email + person?.phone + person?.notes
    }

    public static def findBySlid(String slid) {
        return (slid ? (Contractor.createCriteria().get { person { eq('slid', slid) } }) : null)
    }

    public static def findByFirstNameAndLastName(String firstName, String lastName) {
        return (firstName ? (Contractor.createCriteria().get {
            person {
                eq('firstName', firstName)
                eq('lastName', lastName)
            }
        }) : null)
    }

    public String getSupervisorName() {
        if (supervisor) {
            return supervisor.name
        } else {
            ""
        }
    }

}
