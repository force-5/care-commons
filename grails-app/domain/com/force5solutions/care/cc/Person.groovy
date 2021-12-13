package com.force5solutions.care.cc

public class Person {

    def sessionFactory

    Date dateCreated
    Date lastUpdated

    Boolean isApproved = true
    String firstName
    String middleName
    String lastName
    String email
    String phone
    String notes
    String slid

    static transients = ['firstMiddleLastName']

    String getFirstMiddleLastName(){
        return ((firstName ? (firstName + ' ') : '') + (middleName ? (middleName + ' ') : '') + lastName ?: '')
    }

    void setSlid(String slid){
        this.slid = slid?.toUpperCase()
    }

    void setEmail(String email){
        this.email = email?.toLowerCase()
    }

    String toString() {
        "${lastName ?: ''}, ${firstName ?: ''} ${middleName ?: ''}"
    }
    
    String toDetailString(){
        return (firstName + middleName + lastName + email + slid + phone + notes)
    }

    static constraints = {
        email(nullable: true, email: true)
        middleName(nullable: true)
        notes(nullable: true)
        slid(nullable: true)
        phone(nullable: true)
    }
}