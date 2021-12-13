package com.force5solutions.care.cc

import com.force5solutions.care.common.CareConstants

class CertificationExpirationNotificationTemplate {

    String certification
    String notificationPeriodInDays
    String taskTemplateName

    static constraints = {
        certification(nullable: false, blank: false, validator: {val, obj ->
            if (obj.id) {
                CertificationExpirationNotificationTemplate template = CertificationExpirationNotificationTemplate.findByCertificationAndNotificationPeriodInDays(val, obj.notificationPeriodInDays)
                if (template && (obj.id != template?.id)) {
                    return 'certification.not.unique.message'
                }
            } else {
                if (val && CertificationExpirationNotificationTemplate.countByCertificationAndNotificationPeriodInDays(val, obj.notificationPeriodInDays)) {
                    return 'certification.not.unique.message'
                }
            }
            return true
        })
        notificationPeriodInDays(nullable: false, blank: false)
        taskTemplateName(nullable: false, blank: false)
    }

    static transients = ['certificationName']

    String toString() {
        return "${certification} : ${notificationPeriodInDays} : ${taskTemplateName}"
    }

    CertificationExpirationNotificationTemplate() {}

    CertificationExpirationNotificationTemplate(String certification, String notificationPeriodInDays, String taskTemplateName) {
        this.certification = certification
        this.notificationPeriodInDays = notificationPeriodInDays
        this.taskTemplateName = taskTemplateName
    }

    public static String findCertificationExpirationNotificationTemplate(WorkerCertification workerCertification, Integer notificationPeriod) {
        String taskTemplate = null

        List<CertificationExpirationNotificationTemplate> certificationExpirationNotificationTemplates = CertificationExpirationNotificationTemplate.list()
        certificationExpirationNotificationTemplates.each {rule ->
            if (((workerCertification.certification.id as String) in rule.certification.tokenize(',')) && ((notificationPeriod as String) in rule.notificationPeriodInDays.tokenize(','))) {
                taskTemplate = rule.taskTemplateName
            }
        }
        if (!taskTemplate) {
            certificationExpirationNotificationTemplates = certificationExpirationNotificationTemplates.findAll {it.certification == CareConstants.CERTIFICATION_EXPIRATION_NOTIFICATION_TEMPLATE_WILDCARD}
            certificationExpirationNotificationTemplates.each {rule ->
                if (rule && ((notificationPeriod as String) in rule.notificationPeriodInDays.tokenize(','))) {
                    taskTemplate = rule.taskTemplateName
                }
            }
        }
        if (!taskTemplate) {
            taskTemplate = CareConstants.WORKFLOW_TASK_TEMPLATE_CERTIFICATION_EXPIRY_NOTIFICATION_GENERAL
        }
        return taskTemplate
    }

    public static List findNotificationPeriodsForCertification(Certification certification) {
        List<Integer> notificationPeriods = []
        List<CertificationExpirationNotificationTemplate> certificationExpirationNotificationTemplates = CertificationExpirationNotificationTemplate.list()
        certificationExpirationNotificationTemplates.each {rule ->
            if (rule.certification == CareConstants.CERTIFICATION_EXPIRATION_NOTIFICATION_TEMPLATE_WILDCARD) {
                rule.notificationPeriodInDays.tokenize(',').each {
                    if (it != CareConstants.CERTIFICATION_EXPIRATION_NOTIFICATION_TEMPLATE_WILDCARD)
                        notificationPeriods.add(it as Integer)
                }
            } else {
                if (((certification.id as String) in rule.certification.tokenize(','))) {
                    rule.notificationPeriodInDays.tokenize(',').each {
                        notificationPeriods.add(it as Integer)
                    }
                }
            }
        }
        notificationPeriods.unique()
        return notificationPeriods
    }

    void setCertification(String certification) {
        certification = certification.trim()
        this.certification = certification.replace(' ', '')
    }

    void setNotificationPeriodInDays(String notificationPeriods) {
        notificationPeriods = notificationPeriods.trim()
        this.notificationPeriodInDays = notificationPeriods.replace(' ', '')
    }

}