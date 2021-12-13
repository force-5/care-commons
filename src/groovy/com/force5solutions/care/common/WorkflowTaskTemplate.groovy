package com.force5solutions.care.common

import com.force5solutions.care.cc.PeriodUnit

abstract class WorkflowTaskTemplate {
    PeriodUnit periodUnit
    Integer period
    String responseForm
    String actorSlids
    String toNotificationSlids
    String toNotificationEmails
    String ccNotificationSlids
    String ccNotificationEmails
    Boolean respectExclusionList = false
}
