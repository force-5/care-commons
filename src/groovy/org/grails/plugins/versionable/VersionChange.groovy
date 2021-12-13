package org.grails.plugins.versionable

class VersionChange {

    String className
    String objectId
    String propertyName;
    String oldValue;
    String newValue;

    VersionChange() {
    }

    VersionChange(String propertyName, String oldValue, String newValue, String className, String objectId) {
        this.propertyName = propertyName
        this.oldValue = oldValue
        this.newValue = newValue
        this.className = className
        this.objectId = objectId
    }

    String toString() {
        return "Property Name: ${propertyName}, Old Value: ${oldValue}, New Value: ${newValue}"
    }
}
