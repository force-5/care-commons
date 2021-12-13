package com.force5solutions.care.cc

class Vendor  {
  Date dateCreated
  Date lastUpdated

  String companyName
  String addressLine1
  String addressLine2
  String city
  String state
  String zipCode
  String phone
  String notes
  Date agreementExpirationDate

  static hasMany = [contractors: Contractor, supervisors: ContractorSupervisor]
  static mappedBy = [contractors: 'primeVendor']

  static constraints = {
    companyName blank: false, unique: true
    addressLine1 blank: false
    addressLine2 blank: true, nullable: true
    city blank: false
    state(blank: false, validator: {val, obj ->
      if (!AppUtil.getStateCode(val)) {
        return "vendor.not.valid.message"
      }
    })
    zipCode blank: false
    phone blank: false
    agreementExpirationDate(blank: true, nullable: true)
    notes(nullable: true)
  }

  public String toString() {
    companyName
  }

  def beforeInsert = {
    state = AppUtil.getStateCode(state)
  }

  def beforeUpdate = {
    state = AppUtil.getStateCode(state)
  }

}
