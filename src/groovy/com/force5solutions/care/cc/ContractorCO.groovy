package com.force5solutions.care.cc

class ContractorCO {
    Long id
    String terminateForCause
    String personId
    String status
    Long locationStatusId
    String roleStatus
    String firstName
    String middleName
    String lastName
    String phone
    String email
    String notes
    Integer birthDay
    Integer birthMonth
    Long primeVendor
    Long supervisor
    Long subVendor
    Long subSupervisor
    String workerNumber
    String formOfId // driving license (It could be an enum as well)
    String slid
    String badgeNumber
    byte[] fileContent
    String workerImageId
    Long businessUnitRequester
    Date certificationExpirationByDate
    String expirationForecastPeriod
    Long certificationId

    def businessUnitRequesters = []
    def courses = []

    public void setBusinessUnitRequesters(burs) {
        businessUnitRequesters = [burs].flatten()*.toLong()
    }

    static constraints = {
        firstName(blank: false)
        lastName(blank: false)
        middleName(nullable: true)
        email(email: true)
        notes(blank: true)
        phone(blank: true)
        status(nullable: true)
        locationStatusId(nullable: true)
        birthDay(min: 0, max: 31)
        birthMonth(min: 0, max: 12)
        primeVendor(nullable: true, blank: true)
        subVendor(blank: true)
        subSupervisor(blank: true)
        businessUnitRequester(blank: true)
        roleStatus(blank: true, nullable: true)
        supervisor(nullable: true, blank: true)
        workerNumber(nullable: true)
        formOfId(nullable: true)
        badgeNumber(nullable: true)
        slid(validator: {val, obj ->
            if (val) {
                Person person = Person.findBySlid(obj.slid)
                Contractor contractor
                if (person) {
                    contractor = Contractor.findByPerson(person)
                    if (contractor && (contractor?.id != obj?.id?.toLong())) {
                        return "ContractorCO.not.unique.message"
                    }
                }
            }
        })
        fileContent maxSize: 50000000, nullable: true
        workerImageId blank: true
        terminateForCause blank: true
        businessUnitRequesters(validator: {val, obj ->
            if (val.size() < 1) {
                return "default.blank.message"
            }
        })
    }

    Map createModelForContractor() {
        List<Vendor> vendors = Vendor.list().sort {it.toString().toLowerCase()}
        List<ContractorSupervisor> supervisors = []//ContractorSupervisor.list()
        Vendor vendor = vendors.find {it.id == this?.primeVendor?.toLong()}
        if (vendor) {
            supervisors = ContractorSupervisor.findAllByVendor(vendor).sort {it.toString().toLowerCase()}
        }
        List<ContractorSupervisor> subSupervisors = []//ContractorSupervisor.list()
        Vendor subVendor = vendors.find {it.id == this?.subVendor?.toLong()}
        if (subVendor) {
            subSupervisors = ContractorSupervisor.findAllByVendor(subVendor).sort {it.toString().toLowerCase()}
        }
        List<BusinessUnitRequester> businessUnitRequesters = BusinessUnitRequester.list()
        List<BusinessUnitRequester> selectedBusinessUnitRequesters = businessUnitRequesters.findAll {it.id in this.businessUnitRequesters }
        List<BusinessUnitRequester> remainingBusinessUnitRequesters = businessUnitRequesters - selectedBusinessUnitRequesters
        List<Course> courses = Course.list().findAll {it.id in this.courses }
        return ['contractorInstance': this,
                'vendors': vendors,
                'selectedBusinessUnitRequesters': selectedBusinessUnitRequesters,
                'remainingBusinessUnitRequesters': remainingBusinessUnitRequesters,
                'supervisors': supervisors, 'subSupervisors': subSupervisors, courses: courses]
    }

    ContractorCO() {
    }

    ContractorCO(Contractor contractor) {
        contractor.person.with {
            this.firstName = firstName
            this.lastName = lastName
            this.middleName = middleName
            this.phone = phone
            this.email = email
            this.slid = slid
            this.notes = notes
        }
        contractor.with {
            this.workerNumber = workerNumber
            this.formOfId = formOfId
            this.badgeNumber = badgeNumber
            this.birthDay = birthDay ?: 0
            this.birthMonth = birthMonth ?: 0
        }
        this.primeVendor = contractor.primeVendor?.id
        this.supervisor = contractor.supervisor?.id
        this.subVendor = contractor.subVendor?.id
        this.subSupervisor = contractor.subSupervisor?.id
        //        cc.procurementOwner = contractor.procurementOwner?.id
        this.businessUnitRequesters = contractor.businessUnitRequesters*.id
        this.courses = contractor.courses*.id
        this.terminateForCause = contractor?.terminateForCause?.id
        this.id = contractor?.id
        this.workerImageId = contractor?.workerImage?.id
    }


}
