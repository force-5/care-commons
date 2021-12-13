package com.force5solutions.care.cc

class EmployeeCO {
    Long id
    String firstName
    String middleName
    String lastName
    String email
    String phone
    String notes
    String slid
    String status
    Long locationStatusId
    String roleStatus
    String title
    String department
    Long supervisor
    String workerNumber
    String badgeNumber
    String terminateForCause
    byte[] fileContent
    String workerImageId
    Long businessUnitRequester
    def businessUnitRequesters = []
    def courses = []
    Date certificationExpirationByDate
    String expirationForecastPeriod
    Long certificationId

    static constraints = {
        firstName(blank: false)
        lastName(blank: false)
        middleName(nullable: true)
        email(email: true)
        status(nullable: true)
        locationStatusId(nullable: true)
        notes(blank: true)
        phone(blank: true)
        title(blank: true)
        department(blank: true)
        workerNumber(blank: true)
        businessUnitRequester(blank: true)
        certificationExpirationByDate(nullable: true)
        certificationId(nullable: true)
        expirationForecastPeriod(blank: true)
        roleStatus(blank: true, nullable: true)

        supervisor(validator: {val, obj ->
            if (val < 1) {
                return "default.blank.message"
            }
        })

        slid(blank: false, validator: {val, obj ->
            if (val) {
                Person person = Person.findBySlid(obj.slid)
                Employee employee
                if (person) {
                    employee = Employee.findByPerson(person)
                    if (employee && (employee?.id != obj?.id?.toLong())) {
                        return "EmployeeCommand.not.unique.message"
                    }
                }
            }
        })

        businessUnitRequesters(validator: {val, obj ->
            if (val.size() < 1) {
                return "default.blank.message"
            }
        })

        badgeNumber(blank: true)
        fileContent maxSize: 50000000, nullable: true
        workerImageId blank: true
        terminateForCause blank: true
    }

    public void setBusinessUnitRequesters(burs) {
        businessUnitRequesters = [burs].flatten()*.toLong()
    }
}