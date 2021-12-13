package com.force5solutions.care.feed

class FeedRunController {

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [feedRunInstanceList: FeedRun.list(params), feedRunInstanceTotal: FeedRun.count()]
    }

    def show = {
        def feedRunInstance = FeedRun.get(params.id)
        if (!feedRunInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'feedRun.label', default: 'FeedRun'), params.id])}"
            redirect(action: "list")
        }
        else {
            [feedRunInstance: feedRunInstance]
        }
    }

    def showDetails = {
        FeedRun feedRun = FeedRun.get(params.id)
        List<FeedRunReportMessage> reportMessages = []
        if (params.type && (FeedReportMessageType."${params.type}" == FeedReportMessageType.EXCEPTION)) {
            reportMessages = feedRun.exceptionMessages
        } else if (params.type && (FeedReportMessageType."${params.type}" == FeedReportMessageType.INFO)) {
            reportMessages = feedRun.infoMessages
        } else if (params.type && (FeedReportMessageType."${params.type}" == FeedReportMessageType.ERROR)) {
            reportMessages = feedRun.errorMessages
        } else if (FeedOperation."${params.operation}" == FeedOperation.PROCESS) {
            reportMessages = feedRun.processMessages
        } else if (FeedOperation."${params.operation}" == FeedOperation.CREATE) {
            reportMessages = feedRun.createMessages
        } else if (FeedOperation."${params.operation}" == FeedOperation.UPDATE) {
            reportMessages = feedRun.updateMessages
        } else if (FeedOperation."${params.operation}" == FeedOperation.DELETE) {
            reportMessages = feedRun.deleteMessages
        }
        render(view: '/feedRun/showDetails', model: [reportMessages: reportMessages])
    }

}
