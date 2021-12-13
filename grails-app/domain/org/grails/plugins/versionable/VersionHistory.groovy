package org.grails.plugins.versionable

import org.codehaus.groovy.grails.commons.DefaultGrailsDomainClass
import org.codehaus.groovy.grails.commons.GrailsDomainClassProperty
import grails.util.GrailsNameUtils
import org.codehaus.groovy.grails.commons.ApplicationHolder
import org.apache.commons.logging.LogFactory

class VersionHistory {
    private static log = LogFactory.getLog(this)
    def grailsApplication

    String contextId = VersioningContext.get()
    String userId
    String className
    String objectId
    String propertyName;
    String propertyNaturalName;
    String oldValueString
    String newValueString
    Date dateCreated;
    Date lastUpdated;
    Date effectiveDate;

    static constraints = {
        dateCreated(nullable: true)
        lastUpdated(nullable: true)
        oldValueString(nullable: true, maxSize: 15000)
        newValueString(nullable: true, maxSize: 15000)
        effectiveDate(nullable: true)
    }

    static transients = ['propertyType', 'propertyNaturalName', 'oldValue', 'newValue', 'object']

    static List<VersionHistoryVO> getHistoryVOs(String className, String objectId) {
        List versionHistories = getHistoryItems(className, objectId)
        Map historiesMap = versionHistories.groupBy {it.contextId}.sort {new Date() - it.value.first().dateCreated}
        log.debug(historiesMap.keySet())
        List histories = []
        VersionHistoryVO historyVO
        historiesMap.each {String contextId, List<VersionHistory> historiesByContextId ->
            histories.add(new VersionHistoryVO(historiesByContextId))
        }
        return histories
    }

    static List<VersionHistory> getHistoryItems(String className, String objectId) {
        List versionHistories = VersionHistory.createCriteria().listDistinct {
            and {
                eq('className', className)
                eq('objectId', objectId)
                isNotNull('effectiveDate')
            }
            order('dateCreated', 'desc')
        }
        return versionHistories
    }

    def getCurrentObject() {
        DefaultGrailsDomainClass domainClass = getDomainClass(className)
        Class idType = domainClass.getPropertyType("id")
        def currentObject = grailsApplication.getClassForName(domainClass.fullName).findById(objectId.asType(idType))
        return currentObject
    }

    def getPropertyNaturalName() {
        GrailsNameUtils.getNaturalName(propertyName)
    }

    def getNewValue() {
        return getActualValue(newValueString)
    }

    void setNewValue(String value) {
        newValueString = value
    }

    void setOldValue(String value) {
        oldValueString = value
    }

    public Class getPropertyType() {
        DefaultGrailsDomainClass domainClass = getDomainClass(className)
        Class type = domainClass.getPropertyType(propertyName)
        return type;
    }


    def getOldValue() {
        return getActualValue(oldValueString)
    }

    private def convertToList(String value) {
        if (!value) {return []}
        value = value.substring(1, value.length() - 1)
        def list = value ? value.tokenize(', ') : []
        list = list.collect {it = it.trim()}
        list = list.findAll {it}
        return list
    }


    public static Boolean hasNewUnapprovedChanges(String className, def objectId, Date date = new Date()) {
        def count = VersionHistory.createCriteria().count {
            and {
                eq('className', className)
                eq('objectId', objectId)
                gt('dateCreated', date)
            }
        }
        return (count as Boolean)
    }

    private def getActualValue(String value) {
        if (value) {
            Class type = propertyType
            Boolean isCollection = Collection.isAssignableFrom(type)
            DefaultGrailsDomainClass propertyDomainClass = getDomainClass(type.name)
            if (isCollection) {
                return getCollectionValue(value)
            } else if (Date.isAssignableFrom(type)) {
                return new Date(value.toLong())
            } else if (propertyDomainClass) {
                Class idType = propertyDomainClass.getPropertyType("id")
                return grailsApplication.getClassForName(propertyDomainClass.fullName).findById(value.asType(idType))
            } else {
                return (type.getSimpleName().equalsIgnoreCase('boolean')) ? value.toBoolean() : value.asType(type)
            }
        } else {
            return null;
        }
    }

    private def getCollectionValue(String value) {
        def valueWithoutBrackets = convertToList(value)
        if (!valueWithoutBrackets) {return valueWithoutBrackets}
        DefaultGrailsDomainClass domainClass = getDomainClass(className)
        GrailsDomainClassProperty property = domainClass.getPropertyByName(propertyName)
        Class propertyCollectionType = property.referencedPropertyType
        if (property.isBasicCollectionType()) {
            valueWithoutBrackets = valueWithoutBrackets.collect {it = it.asType(propertyCollectionType)}
            valueWithoutBrackets = valueWithoutBrackets.asType(propertyType)
            return valueWithoutBrackets
        } else {
            def domainObjects = []
            DefaultGrailsDomainClass referencedDomainClass = getDomainClass(propertyCollectionType.name)
            if (referencedDomainClass) {
                Class idType = referencedDomainClass.getPropertyType("id")
                valueWithoutBrackets = valueWithoutBrackets.collect {it = it.asType(idType)}
                domainObjects = grailsApplication.getClassForName(referencedDomainClass.fullName).findAllByIdInList(valueWithoutBrackets)
                domainObjects = domainObjects.asType(propertyType)
            }
            return domainObjects
        }
    }

    private DefaultGrailsDomainClass getDomainClass(String domainClassName) {
        return grailsApplication.domainClasses.find { it.fullName == domainClassName }
    }


    public String toString() {
        return "VersionHistory{" +
                "id='" + id + '\'' +
                "className='" + className + '\'' +
                ", objectId='" + objectId + '\'' +
                ", propertyName='" + propertyName + '\'' +
                ", oldValue='" + oldValue + '\'' +
                ", newValue='" + newValue + '\'' +
                ", dateCreated=" + dateCreated +
                ", lastUpdated=" + lastUpdated +
                ", effectiveDate=" + effectiveDate +
                '}';
    }
}
