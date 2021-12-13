package com.force5solutions.care.ldap

class LoginCO {
    String slid
    String password

    static constraints = {
        slid(blank: false)
        password(blank: false)
    }
}
