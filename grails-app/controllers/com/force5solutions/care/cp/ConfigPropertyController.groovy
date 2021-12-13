package com.force5solutions.care.cp

class ConfigPropertyController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        List<ConfigProperty> configPropertyInstanceList = []
        Integer configPropertyTotal = null
        Integer offset = params.offset ? params.offset.toInteger() : 0
        params.max = (params.rowCount && !params?.rowCount?.equalsIgnoreCase('Unlimited')) ? params?.rowCount?.toInteger() : (params.max ? params.max.toInteger() : 10)
        params.order = params.order ?: 'asc'
        params.sort = params.sort ?: 'name'
        if (!params?.rowCount?.equalsIgnoreCase('Unlimited')) {
            configPropertyInstanceList = ConfigProperty.list(params)
            configPropertyTotal = ConfigProperty.count()
        } else {
            configPropertyInstanceList = ConfigProperty.list()
        }
        if (params?.ajax?.equalsIgnoreCase('true')) {
            render template: 'configPropertiesTable', model: [configPropertyInstanceList: configPropertyInstanceList, configPropertyTotal: configPropertyTotal, offset: offset, max: params.max]
        } else {
            [configPropertyInstanceList: configPropertyInstanceList, configPropertyTotal: configPropertyTotal, offset: offset, max: params.max]
        }
    }

    def create = {
        def configPropertyInstance = new ConfigProperty()
        configPropertyInstance.properties = params
        return [configPropertyInstance: configPropertyInstance]
    }

    def save = {
        def configPropertyInstance = new ConfigProperty(params)
        if (configPropertyInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'configProperty.label', default: 'ConfigProperty'), configPropertyInstance.id])}"
            redirect(action: "show", id: configPropertyInstance.id)
        }
        else {
            render(view: "create", model: [configPropertyInstance: configPropertyInstance])
        }
    }

    def show = {
        def configPropertyInstance = ConfigProperty.get(params.id)
        if (!configPropertyInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'configProperty.label', default: 'ConfigProperty'), params.id])}"
            redirect(action: "list")
        }
        else {
            [configPropertyInstance: configPropertyInstance]
        }
    }

    def edit = {
        def configPropertyInstance = ConfigProperty.get(params.id)
        if (!configPropertyInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'configProperty.label', default: 'ConfigProperty'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [configPropertyInstance: configPropertyInstance]
        }
    }

    def update = {
        def configPropertyInstance = ConfigProperty.get(params.id)
        if (configPropertyInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (configPropertyInstance.version > version) {

                    configPropertyInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'configProperty.label', default: 'ConfigProperty')] as Object[], "Another user has updated this ConfigProperty while you were editing")
                    render(view: "edit", model: [configPropertyInstance: configPropertyInstance])
                    return
                }
            }
            configPropertyInstance.properties = params
            if (!configPropertyInstance.hasErrors() && configPropertyInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'configProperty.label', default: 'ConfigProperty'), configPropertyInstance.id])}"
                redirect(action: "show", id: configPropertyInstance.id)
            }
            else {
                render(view: "edit", model: [configPropertyInstance: configPropertyInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'configProperty.label', default: 'ConfigProperty'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def configPropertyInstance = ConfigProperty.get(params.id)
        if (configPropertyInstance) {
            try {
                configPropertyInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'configProperty.label', default: 'ConfigProperty'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'configProperty.label', default: 'ConfigProperty'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'configProperty.label', default: 'ConfigProperty'), params.id])}"
            redirect(action: "list")
        }
    }
}
