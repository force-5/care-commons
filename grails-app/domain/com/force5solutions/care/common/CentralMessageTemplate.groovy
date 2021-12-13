package com.force5solutions.care.common

import com.force5solutions.care.cc.CentralDataFile

class CentralMessageTemplate extends MessageTemplate {
    Set<CentralDataFile> attachments = []

    static hasMany = [attachments: CentralDataFile]
    static transients = ['body', 'subject']

    static constraints = {
        name(unique: true, blank: false)
        subjectTemplate(maxSize: 5000)
        bodyTemplate(maxSize: 8000)
        attachments(nullable: true)
    }

    String toString() {
        return name
    }

}