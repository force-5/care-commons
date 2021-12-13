package com.force5solutions.care.feed

class FeedRunReportMessageDetail {

    FeedRunReportMessage feedRunReportMessage
    static belongsTo = [feedRunReportMessage: FeedRunReportMessage]
    String entityId
    String updatedFields
    String entityType
    String param1
    String param2
    String param3
    Date dateCreated

    static transients = ['entitlementNotFoundExceptionFromCategoryAreaReaderFeed']

    static constraints = {
        dateCreated(nullable: true)
        updatedFields(nullable: true)
        param1(nullable: true)
        param2(nullable: true)
        param3(nullable: true)
    }

    String toString() {
        return entitlementNotFoundExceptionFromCategoryAreaReaderFeed ? entityType + " : " + param1 : (entityType + " : " + param1 + "${(param2) ? (' : ' + param2) : ''}" + "${(param3) ? (' : ' + param3) : ''}")
    }

    public boolean isEntitlementNotFoundExceptionFromCategoryAreaReaderFeed() {
        return (feedRunReportMessage.type.equals(FeedReportMessageType.EXCEPTION) && feedRunReportMessage.operation in [FeedOperation.CREATE, FeedOperation.UPDATE] && feedRunReportMessage.feedRun.feedName.equalsIgnoreCase('Category Area Reader Feed') && feedRunReportMessage.message in ['Entitlement not found for category in the feed', 'Exception occurred while updating Entitlement']) ?: false
    }
}
