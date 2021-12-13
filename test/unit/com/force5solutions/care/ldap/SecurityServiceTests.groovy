package com.force5solutions.care.ldap

import grails.test.GrailsUnitTestCase
import org.codehaus.groovy.grails.plugins.codecs.MD5Codec

class SecurityServiceTests extends GrailsUnitTestCase {

    SecurityService service;

    @Override
    protected void setUp() {
        super.setUp()
        mockDomain(TopUser)
        loadCodec(MD5Codec)
        mockConfig('''
                top.activeDirectory.domain = "f5lab.local"
                top.activeDirectory.ldapHost = "ldap://173.162.65.181";
                top.activeDirectory.searchBase = "dc=f5lab,dc=local"
                top.activeDirectory.securityAuthentication = "simple"
            '''
        )
        service = new SecurityService()
        service.activeDirectoryUtilService = new ActiveDirectoryUtilService();
        new TopUser(slid: "admin", password: "admin", rolesString: "CAREADMIN,CAREEDITOR").save()
    }

    public void testAuthenticationValidUser() {
        assertTrue(service.isValidUser("topuser1", "topuser1"));
        assertFalse(service.isValidUser("admin", "admin"))
    }

    public void testAuthenticationInValidUser() {
        assertFalse(service.isValidUser("topuserDoesNotExist", "topuser1"));
        assertFalse(service.isValidUser("admin", "admin"))
    }

    public void testAuthenticationInValidPassword() {
        assertFalse(service.isValidUser("topuser1", "invalidPassword"));
        assertFalse(service.isValidUser("admin", "admin"))
    }

    public void testLdapSimulationValidUser() {
        mockConfig('''
                top.activeDirectory.simulate = "true"
            '''
        )
        assertTrue(service.isValidUser("admin", "admin"))
    }

    public void testLdapSimulationInValidUser() {
        mockConfig('''
                top.activeDirectory.simulate = "true"
            '''
        )
        assertTrue(service.isValidUser("admin", "admin"))
        assertFalse(service.isValidUser("admin1", "admin1"))
    }
}
