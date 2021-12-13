package com.force5solutions.care.feed

class FeedRunReportMessageController {

    def show = {
        def feedRunReportMessageInstance = FeedRunReportMessage.get(params.id)
        if (!feedRunReportMessageInstance) {
            response.sendError(404)
        }
        else {
            [feedRunReportMessageInstance: feedRunReportMessageInstance]
        }
    }

}
