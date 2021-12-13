package com.force5solutions.care.ldap

import com.force5solutions.care.common.Secured
import org.codehaus.groovy.grails.commons.ConfigurationHolder

class SecurityRoleController {

    def index = {
        redirect(action: list)
    }

    @Secured(value = Permission.READ_SECURITY_ROLE)
    def show = {
        SecurityRole role = SecurityRole.get(params.long("id"))
        if (role) {
            render(view: 'show', model: [role: role])
        } else {
            flash.message = "No such role found"
            redirect(action: list)
        }
    }

    def updateAndGetDefaultSizeOfListViewInConfig(Map params) {
        def defaultSize = null
        if (session && !session.rowCount) {
            session.rowCount = ConfigurationHolder?.config?.defaultSizeOfListView
            defaultSize = session.rowCount.toString().equalsIgnoreCase('Unlimited') ? session.rowCount.toString() : session.rowCount.toInteger()
        } else {
            if (params.rowCount && !params?.rowCount?.toString()?.equalsIgnoreCase('Unlimited')) {
                session.rowCount = params?.rowCount?.toInteger()
                defaultSize = session.rowCount
            } else if (params?.max) {
                session.rowCount = params.max.toInteger()
                defaultSize = session.rowCount
            } else if (params?.rowCount?.toString()?.equalsIgnoreCase('Unlimited')) {
                session.rowCount = params?.rowCount
                defaultSize = session.rowCount
            } else {
                defaultSize = session.rowCount ? (session.rowCount?.toString()?.equalsIgnoreCase('Unlimited') ? session.rowCount : session.rowCount?.toInteger()) : 10
            }
        }
        return defaultSize
    }

    @Secured(value = Permission.READ_SECURITY_ROLE)
    def list = {
        List<SecurityRole> securityRoleList = []
        Integer securityRoleTotal = null
        Integer offset = params.offset ? params.offset.toInteger() : 0
        params.max = updateAndGetDefaultSizeOfListViewInConfig(params)
        if (!params?.max?.toString()?.equalsIgnoreCase('Unlimited')) {
            securityRoleList = SecurityRole.list(params)
            securityRoleTotal = SecurityRole.count()
        } else {
            securityRoleList = SecurityRole.list()
        }
        if (params?.ajax?.equalsIgnoreCase('true')) {
            render template: 'securityRolesTable', model: [roles: securityRoleList, securityRoleTotal: securityRoleTotal, offset: offset, max: params.max]
        } else {
            [roles: securityRoleList, securityRoleTotal: securityRoleTotal, offset: offset, max: params.max]
        }
    }

    @Secured(value = Permission.CREATE_SECURITY_ROLE)
    def create = {
        List<SecurityRole> roles = SecurityRole.list()
        SecurityRole role = new SecurityRole()
        render(view: 'create', model: [roles: roles, role: role])
    }

    def save = {
        SecurityRole securityRole = new SecurityRole()
        securityRole.name = params.name
        securityRole.description = params.roleDescription
        securityRole.emails = params.emails
        securityRole.s()
        if (!securityRole.hasErrors() && securityRole.s()) {
            params?.each {key, value ->
                Permission permission = Permission.values().find {it.name() == key}
                if (permission) {
                    Long permissionLevel = 1L
                    value.each {permissionLevel *= it?.toLong()}
                    new PermissionLevel(role: securityRole, permission: permission, level: permissionLevel).s()
                }
            }
            redirect(action: show, id: securityRole.id)
        }
        else {
            List<SecurityRole> roles = SecurityRole.list()
            flash.message = "Please enter values in the required fileds"
            render(view: 'create', model: [roles: roles, role: securityRole])
        }
    }

    @Secured(value = Permission.UPDATE_SECURITY_ROLE)
    def edit = {
        List<SecurityRole> roles = SecurityRole.list()
        SecurityRole role = SecurityRole.get(params.long('id'))
        if (role) {
            render(view: 'edit', model: [role: role, roles: roles])
        } else {
            flash.message = "No such role found"
            redirect(action: list)
        }
    }

    @Secured(value = Permission.UPDATE_SECURITY_ROLE)
    def update = {
        SecurityRole securityRole = SecurityRole.get(params.long("id"))
        if (securityRole) {
            securityRole.name = params.name
            securityRole.description = params.roleDescription
            securityRole.emails = params.emails
            securityRole.s()
            if (securityRole.permissionLevels) {
                Set<PermissionLevel> permissionLevelsToRemove = securityRole.permissionLevels
                securityRole.permissionLevels = []
                permissionLevelsToRemove?.each {it.delete(flush: true)}
            }
            params?.each {key, value ->
                Permission permission = Permission.values().find {it.name() == key}
                if (permission) {
                    Long permissionLevel = 1L
                    value.each {permissionLevel *= it?.toLong()}
                    new PermissionLevel(role: securityRole, permission: permission, level: permissionLevel).s()
                }
            }

            redirect(action: show, id: securityRole.id)
        } else {
            flash.message = "No such role found"
            redirect(action: list)
        }
    }

    @Secured(value = Permission.DELETE_SECURITY_ROLE)
    def delete = {
        SecurityRole securityRole = SecurityRole.get(params.long("id"))
        if (securityRole) {
            String name = securityRole.name
            securityRole.delete(flush: true)
            flash.message = "Role ${name} deleted"
            redirect(action: list)
        } else {
            flash.message = "No such role found"
            redirect(action: list)
        }
    }
    def getPermissionsForSecurityRole = {
        SecurityRole securityRole = SecurityRole.get(params.long("roleId"))
        render(template: 'permissions', model: [role: securityRole])
    }
}

class SecurityRoleVO {
    String name
    String description
    Set<PermissionVO> permissions = []
}

class PermissionVO {
    Permission permission
    Long value
}
