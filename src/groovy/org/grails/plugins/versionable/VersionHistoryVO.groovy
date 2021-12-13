package org.grails.plugins.versionable

class VersionHistoryVO {
    String type
    Date date
    String userId
    String description
    Boolean showChanges
    String id

    VersionHistoryVO(List<VersionHistory> historiesByContextId, String objectString) {
        this.id = historiesByContextId.first().contextId
        this.type = "UPDATE"
        this.userId = historiesByContextId.first().userId
        this.description = "Updated ${objectString}: " + historiesByContextId*.propertyNaturalName.join(', ')
        this.date = historiesByContextId.first().dateCreated
        this.showChanges = true
    }

    VersionHistoryVO() {
    }

    public String toString() {
        return "VersionHistoryVO{" +
                "type='" + type + '\'' +
                ", date=" + date +
                ", userId='" + userId + '\'' +
                ", description='" + description + '\'' +
                ", showChanges=" + showChanges +
                ", id='" + id + '\'' +
                '}';
    }
}
