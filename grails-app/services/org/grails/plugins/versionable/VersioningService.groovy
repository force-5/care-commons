package org.grails.plugins.versionable

import org.codehaus.groovy.grails.commons.DefaultGrailsDomainClass
import org.codehaus.groovy.grails.commons.ConfigurationHolder
import org.springframework.web.context.request.RequestContextHolder
import org.codehaus.groovy.grails.commons.GrailsDomainClassProperty
import com.force5solutions.care.common.CareConstants

class VersioningService {

    def grailsApplication;
    def sessionFactory;
    static transactional = true


    private void printMaps(oldMap, newMap) {
//        log.debug "OLD MAP"
//        oldMap.each {key, value ->
//            log.debug "key : ${key}, value : ${value}"
//        }
//
//        log.debug "NEW MAP"
//        newMap.each {key, value ->
//            log.debug "key : ${key}, value : ${value}"
//        }
    }

    public boolean isDomainClassProperty(String className, String propertyName) {
//        log.debug "Will now find if ${propertyName} is a domain class property of ${className}"
        DefaultGrailsDomainClass domainClass = grailsApplication.domainClasses.find { it.fullName == className }
        Class propertyClass = domainClass.getPropertyType(propertyName)
        DefaultGrailsDomainClass propertyDomainClass = grailsApplication.domainClasses.find { it.fullName == propertyClass.name }
        return (propertyDomainClass ? true : false)
    }

    public boolean isCollection(String className, String propertyName) {
        DefaultGrailsDomainClass domainClass = grailsApplication.domainClasses.find { it.fullName == className }
        Class propertyClass = domainClass.getPropertyType(propertyName)
        return Collection.isAssignableFrom(propertyClass)
    }

    public boolean isBasicCollectionType(String className, String propertyName) {
        DefaultGrailsDomainClass domainClass = grailsApplication.domainClasses.find { it.fullName == className }
        GrailsDomainClassProperty property = domainClass.getPropertyByName(propertyName)
        return property.isBasicCollectionType()
    }

    public boolean isEnum(String className, String propertyName) {
        DefaultGrailsDomainClass domainClass = grailsApplication.domainClasses.find { it.fullName == className }
        GrailsDomainClassProperty property = domainClass.getPropertyByName(propertyName)
        return property.isEnum()
    }

    public boolean isDate(String className, String propertyName) {
        DefaultGrailsDomainClass domainClass = grailsApplication.domainClasses.find { it.fullName == className }
        GrailsDomainClassProperty property = domainClass.getPropertyByName(propertyName)
        return Date.isAssignableFrom(property.type)
    }

    public Set<VersionChange> capturePropertyChanges(def object, Map oldMap, Map newMap) {
        Set<VersionChange> propertyChanges = []
        VersionChange propertyChange
        String propertyName
        List<String> ignoreList = ['version', 'lastUpdated', 'dateCreated']
        oldMap.each({ key, oldVal ->
            String oldValueString, newValueString;
            propertyName = key.toString()
            if (!(key.toString() in ignoreList) && (oldVal?.toString() != newMap[key]?.toString())) {
                boolean isDomainClassProperty = isDomainClassProperty(object.class.name, propertyName)
                boolean isCollection = isCollection(object.class.name, propertyName)
                boolean isEnum = isEnum(object.class.name, propertyName)
                boolean isDate = isDate(object.class.name, propertyName)
                boolean isBasicCollection = isBasicCollectionType(object.class.name, propertyName)
                if (isCollection && !isBasicCollection) {
                    oldValueString = oldMap[propertyName]*.id
                    newValueString = newMap[propertyName]*.id
                } else if (isDomainClassProperty) {
                    oldValueString = oldMap[propertyName]?.id
                    newValueString = newMap[propertyName]?.id
                } else if (isEnum) {
                    oldValueString = oldMap[propertyName]?.name()
                    newValueString = newMap[propertyName]?.name()
                } else if (isDate) {
                    oldValueString = oldMap[propertyName]?.time
                    newValueString = newMap[propertyName]?.time
                } else {
                    oldValueString = oldVal?.toString();
                    newValueString = newMap[key]?.toString();
                }
                propertyChange = new VersionChange(propertyName, oldValueString, newValueString, object.class.name, object.id.toString())

                object.setProperty(propertyName, object.getPersistentValue(propertyName))

                propertyChanges.add(propertyChange)
            }
        })
        return propertyChanges;
    }

    List<String> getEmbeddedObjectNames(def object) {
        if (object.hasProperty('embeddedObjects')) {
            return object.embeddedObjects
        } else {
            return []
        }
    }

    List<String> getAttachedObjectNames(def object) {
        if (object.hasProperty('attachedObjects')) {
            return object.attachedObjects
        } else {
            return []
        }
    }

    List<Object> getAllEmbeddedObjects(def object) {
        List<Object> objects = [object]
        List<String> objectNames = getEmbeddedObjectNames(object)
        objectNames.each { objectName ->
            objects.addAll(getAllEmbeddedObjects(object.getProperty(objectName)))
        }
        return objects
    }

    List<Object> getAllAttachedObjects(def object) {
        List<Object> objects = []
        List<String> objectNames = getAttachedObjectNames(object)
        objectNames.each { objectName ->
            objects.addAll(object.getProperty(objectName))
        }
        return objects.flatten()
    }

    Set<VersionChange> populateVersionChanges(def object) {
        Set<VersionChange> changes = []
        List dirtyProperties = object.getDirtyPropertyNames()
        List embeddedObjects = getEmbeddedObjectNames(object)
        Map oldMap = [:]
        Map newMap = [:]

//        log.debug "Dirty Properties for object ${object} of class ${object.class.name} are : ${dirtyProperties}"

        if (dirtyProperties) {
            dirtyProperties.each { String dirtyProperty ->
                oldMap[dirtyProperty] = object.getPersistentValue(dirtyProperty)
                newMap[dirtyProperty] = object.getProperty(dirtyProperty)
            }
        }
        printMaps(oldMap, newMap)
        oldMap = sortCollectionValuesInTheMapForCorrectEquality(oldMap)
        newMap = sortCollectionValuesInTheMapForCorrectEquality(newMap)

        changes.addAll(capturePropertyChanges(object, oldMap, newMap))

        embeddedObjects.each { String propertyName ->
            def property = object.getProperty(propertyName)
//            log.debug "Property : ${property} of class ${property.class.name}, with name ${propertyName}"
            changes.addAll(populateVersionChanges(property));
        }
        return changes;
    }

    private Map sortCollectionValuesInTheMapForCorrectEquality(LinkedHashMap map) {
        map.each { key, value ->
            if (value instanceof Collection) {
                value = value.sort()
            }
        }
        return map
    }

    private moveChangesToHistory(def object) {
//        log.debug "************************************* Inside moveChangesToHistory ************************************* "
        Set<VersionChange> changes = populateVersionChanges(object)

//        log.debug "Changes: "
//        changes.each {
//            log.debug it
//        }
        List oldHistory = []
        changes.each { VersionChange versionChange ->
            oldHistory.addAll(VersionHistory.findAllWhere([effectiveDate: null, objectId: versionChange.objectId, className: versionChange.className]))
        }

        if (oldHistory) {
            oldHistory*.delete()
        }

        changes.each { VersionChange change ->
            VersionHistory history = new VersionHistory();
            history.className = change.className
            history.objectId = change.objectId
            history.propertyName = change.propertyName
            history.oldValue = change.oldValue
            history.newValue = change.newValue
            String userId

            if (ConfigurationHolder.config.bootStrapMode) {
                userId = CareConstants.CENTRAL_SYSTEM
            } else {
                try {
                    def session = RequestContextHolder?.currentRequestAttributes()?.getSession()
                    if (session) {
                        userId = session.loggedUser
                    }
                } catch (Exception e) {
                } finally {
                    userId = userId ? userId : CareConstants.CENTRAL_SYSTEM
                }
            }
            history.userId = userId
//                (ConfigurationHolder.config.bootStrapMode) ? 'CARE System' : (RequestContextHolder?.currentRequestAttributes()?.getSession()?.loggedUser ?: 'CARE System')
            history.save()
//            log.debug "History: " + history
        }
    }

    public void injectIgnoreChangesProperty() {
//        grailsApplication.domainClasses.each {DefaultGrailsDomainClass domainClass ->
//            if (domainClass.hasProperty('versionable') && domainClass.getStaticPropertyValue('versionable', Boolean)) {
//                domainClass.metaClass.ignoreVersionChanges = false
//            }
//        }
    }

    public def mergeChanges(def object, List<VersionHistory> changes, boolean save = true) {
        changes.each { VersionHistory change ->
            object.setProperty(change.propertyName, change.newValue)
        }
        if (save) {
            if (object.hasProperty('isPropagated') as Boolean) {
                object.isPropagated = false
            }
            object.save(flush: true)
        }
        return object;
    }

    public def reverseChanges(def object, List<VersionHistory> changes) {
        changes.each { VersionHistory change ->
            object.setProperty(change.propertyName, change.oldValue)
        }
        return object;
    }

    public def hasPendingChanges(def object) {
        def count = VersionHistory.createCriteria().count {
            and {
                isNull('effectiveDate')
                eq('objectId', object.id.toString())
                eq('className', object.class.name)
            }
        }
        return (count as Boolean)
    }

    public def getPendingChanges(def object) {
        List<VersionHistory> changes = VersionHistory.findAllWhere([effectiveDate: null, objectId: object.id.toString(), className: object.class.name])
        return changes
    }

    public def getAppliedChanges(def object, Date startDate, Date endDate = new Date()) {
        List<VersionHistory> changes = VersionHistory.createCriteria().list {
            and {
                eq("objectId", object.id.toString())
                isNotNull("effectiveDate")
                eq("className", object.class.name)
                between("effectiveDate", startDate, endDate)
            }
            order("id", "desc")
        }

        return changes;
    }

    public def approvePendingChanges(def object) {
        object = object.refresh();
        List changes = getPendingChanges(object)
        Date effectiveDate = new Date();
        object = mergeChanges(object, changes)
        changes.each { VersionHistory history ->
            history.effectiveDate = effectiveDate
            history.save(flush: true)
        }
//        log.debug "Approved Object: " + object
        object.isApproved = true;
        saveVersionableObject(object)
        return object;
    }

    public void saveObject(def object, Boolean overrideConditionForCreatingHistory) {
        Boolean isApproved = object.getProperty('isApproved')
//        log.debug "Is Approved: " + isApproved
        if (isApproved || overrideConditionForCreatingHistory) {
//            log.debug "Moving Changes to History: ${object}"
            moveChangesToHistory(object)
            object.save(flush: true)
            object = object.refresh()

//            log.debug "History Count: " + VersionHistory.count()
//            log.debug "Old Object: ${object}"
        } else {
            object.save(flush: true)
//            log.debug "Saved changes to unapproved object: ${object}"
        }
    }

    public void saveVersionableObject(Object object, Boolean overrideConditionForCreatingHistory = false) {
//        log.debug "################# Inside Save Versionable Object: ${object}"
        if (object.id) {
//            log.debug "Updating object"
            saveObject(object, overrideConditionForCreatingHistory)
            List<Object> objects = getAllEmbeddedObjects(object)
            objects.each { obj ->
                autoApproveUnVersionedFields(obj)
            }
        } else {
//            log.debug "Creating object"
            object.save(flush: true)
        }
    }

    public void autoApproveUnVersionedFields(Object object) {
//        log.debug "Auto approving unaudited fields, History Count: " + VersionHistory.count()
        List changes = getPendingChanges(object)
        Date effectiveDate = new Date()
        Set<VersionHistory> changesToBeMerged = []
        changes.each { VersionHistory history ->
            if (!(object.hasProperty('requireApprovalObjects') && (history.propertyName in object.'requireApprovalObjects'))) {
                history.effectiveDate = effectiveDate
                changesToBeMerged << history;
                history.save(flush: true, failOnError: true)
            }
        }
        object = mergeChanges(object, changesToBeMerged as List)
    }

    public def getCurrentObject(def object) {
        object = object.discard();
        List changes = getPendingChanges(object)
        object = mergeChanges(object, changes, false)
        return object;
    }

    public def getObjectOnDate(def object, Date date) {
//        log.debug "****************** Getting Object on Date ****************** "
        object.discard()
        List changes = getAppliedChanges(object, date)
        object = reverseChanges(object, changes)
        if (object.hasProperty('embeddedObjects')) {
            object.embeddedObjects.each { propertyName ->
                def oldVersionableObject = getObjectOnDate(object.getProperty(propertyName), date)
                object.setProperty(propertyName, oldVersionableObject)
            }
        }
        return object;
    }

    public List<VersionHistory> getHistoryItems(def object) {
//        log.debug "############## getHistoryItems ################"
        List<Object> objects = getAllEmbeddedObjects(object)
//        objects.each {
//            log.debug "Embedded Object: ${object.class.name}, ${it.class.name}"
//        }
        List<Object> attachedObjects = getAllAttachedObjects(object)
//        attachedObjects.each {
//            log.debug "Attached Object: ${object.class.name}, ${it.class.name}"
//        }
        List<VersionHistory> versionHistoryItems = []
        objects.each { obj ->
            versionHistoryItems.addAll(VersionHistory.getHistoryItems(obj.class.name, obj.id.toString()));
        }
        attachedObjects.each { obj ->
            versionHistoryItems.addAll(VersionHistory.getHistoryItems(obj.class.name, obj.id.toString()));
        }

//        versionHistoryItems.each {
//            log.debug it
//        }
        return (versionHistoryItems.findAll { it })
    }

}
