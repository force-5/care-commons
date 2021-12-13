package org.grails.plugins.versionable

class VersionableTagLib {

    static namespace = "versionable"
    def grailsApplication
    def versioningService

    def showHistory = {attrs ->
        Object object = attrs['object']
        List<VersionHistory> versionHistories = versioningService.getHistoryItems(object)
        List historyVOs = []
        if (versionHistories) {
            Map versionHistoriesByContextId = versionHistories.groupBy {it.contextId}
            VersionHistoryVO historyVO
            versionHistoriesByContextId.each {String contextId, List<VersionHistory> historiesByContextId ->
                String objectString = historiesByContextId.first().currentObject?.toString()
                if(objectString == object.toString()) { objectString = '' }
                historyVOs.add(new VersionHistoryVO(historiesByContextId, objectString))
            }
        }
        VersionHistoryVO historyVO = new VersionHistoryVO()
        historyVO.type = "CREATE"
        historyVO.userId = ''
        historyVO.description = "New ${object.class.simpleName} ${object.toString()} created"
        historyVO.date = object.dateCreated
        historyVO.showChanges = false
        historyVOs.add(historyVO)
        historyVOs = historyVOs.sort{it.date}.reverse()
        out << g.render(template: '/versionHistory/historyTable', model: [histories: historyVOs])
    }

    def showUnapprovedChanges = {attrs ->
        Object object = attrs['object']
        List changes = versioningService.getPendingChanges(object)
        out << g.render(template: '/versionHistory/unapprovedChangesTable', model: [changes: changes])
    }

    def hasUnapprovedChanges = { attrs, body ->
        Object object = attrs['object']
        if (versioningService.hasPendingChanges(object)) {
            out << body()
        }
    }
}
