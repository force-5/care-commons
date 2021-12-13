package org.grails.plugins.versionable

class VersionHistoryController {

    def showHistory = {
        List<VersionHistory> histories = VersionHistory.findAllByContextIdAndEffectiveDateIsNotNull(params.id)
        List<VersionChange> versionChanges = []
        histories.each{VersionHistory history ->
            versionChanges.add(new VersionChange(history.propertyNaturalName, history.oldValue?.toString(), history.newValue?.toString(), history.className, history.objectId))
        }
        String objectString = histories.first().currentObject?.toString()
        render(template: '/versionHistory/historyPopup', model: [objectString: objectString, 'propertyChanges': versionChanges])
    }

}
