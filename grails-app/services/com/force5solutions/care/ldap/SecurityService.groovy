package com.force5solutions.care.ldap

import org.codehaus.groovy.grails.commons.ConfigurationHolder

class SecurityService {

    def activeDirectoryUtilService
    boolean transactional = true

    public boolean isValidUser(String slid, String password) {
        return (getUser(slid, password) ? true : false)
    }

    public TopUser getUser(String slid, String password) {
        TopUser user
        if (ConfigurationHolder.config.top.activeDirectory.simulate == "true") {
            log.info "Not going to active directory."
            user = TopUser.findBySlidAndPassword(slid, password)
        } else {
            user = activeDirectoryUtilService.login(slid, password)
        }
        return user
    }
}