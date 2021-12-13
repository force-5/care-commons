package com.force5solutions.care.ldap

import org.codehaus.groovy.grails.commons.ConfigurationHolder

class TopUser {

    String slid
    String password
    String rolesString

    static transients = ['roles']

    String toString(){
        return "${slid} : ${rolesString}"
    }

    Set<String> getRoles(){
        List<String> securityRoleNames = SecurityRole.list()*.name
        Set<String> securityRoles = rolesString ? rolesString.tokenize(',')*.trim() : []
        securityRoles = securityRoles.findAll{it in securityRoleNames}
        String defaultSecurityGroup = ConfigurationHolder.config.defaultSecurityGroup
        if(defaultSecurityGroup && (defaultSecurityGroup in securityRoleNames)){
           securityRoles.add(defaultSecurityGroup)
        }
        return (securityRoles)
    }

    static constraints = {
        slid(blank: false, unique: true)
        password(blank: false)
        rolesString(nullable: true)
    }
}
