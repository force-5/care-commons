package com.force5solutions.care.ldap

import org.codehaus.groovy.grails.commons.ConfigurationHolder
import com.force5solutions.care.cc.EmployeeSupervisor

class LoginController {

    def securityService

    def index = {
        def homepage = ConfigurationHolder.config.homepageController
        def defaultAction = ConfigurationHolder.config.homepageAction ?: 'index'
        if (!session.loggedUser) {
            render(view: 'login', model: [loginCO: new LoginCO()], params: params)
        } else {
            redirect(controller: homepage, action: defaultAction)
        }
    }

    def login = {LoginCO loginCO ->
        def homepage = ConfigurationHolder.config.homepageController
        def defaultAction = ConfigurationHolder.config.homepageAction ?: 'index'
        TopUser user = securityService.getUser(loginCO.slid, loginCO.password)
        if (user) {
            session.roles = user.roles
            session.loggedUser = loginCO.slid
            if (params.targetUri && params.targetUri != "null") {
                redirect(uri: params.targetUri)
                params.remove("targetUri")
            } else {
                redirect(controller: homepage, action: defaultAction)
            }
        }
        else {
            flash.errorMessage = message(code: 'default.auth.failed.message')
            render(view: 'login', model: [loginCO: new LoginCO()], params: params)
        }
    }

    def logout = {
        session.invalidate()
        params.remove("targetUri")
        redirect(controller: 'login', action: 'index')
    }


}

