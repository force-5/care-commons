package com.force5solutions.care.cp

import org.codehaus.groovy.grails.commons.ConfigurationHolder
import org.springframework.mail.javamail.JavaMailSenderImpl
import org.codehaus.groovy.grails.commons.ApplicationHolder
import org.quartz.CronTrigger

class ConfigProperty {

    def quartzScheduler
    static config = ConfigurationHolder.config

    Date dateCreated
    Date lastUpdated
    String name
    String value

//    void setValue(String value){
//        this.value = (ConfigurationHolder.config.useEncryption && (name in ConfigurationHolder.config.encryptionProperties)) ? value.encodeAsBase64() : value
//    }
//
//    String getValue(){
//        return ((ConfigurationHolder.config.useEncryption && (name in ConfigurationHolder.config.encryptionProperties)) ? value.decodeBase64() : value)
//    }

    static constraints = {
        name(unique: true, blank: false)
        value(blank: false)
    }

    def beforeInsert = {
        updateConfigPropertyMap()
    }

    def beforeUpdate = {
        updateConfigPropertyMap()
    }

    def beforeDelete = {
        removeConfigPropertyFromTheConfigMap(config, name)
    }

    void removeConfigPropertyFromTheConfigMap(Map configMap, String key) {
        List<String> tokenizedString = key.tokenize('.')
        if (tokenizedString.size().equals(1)) {
            configMap.remove(tokenizedString[0])
        } else {
            String subKey = tokenizedString[0]
            tokenizedString.remove(tokenizedString[0])
            removeConfigPropertyFromTheConfigMap(configMap[subKey] as Map, tokenizedString.join('.'))
        }
    }

    void updateConfigPropertyMap() {
        List props = name.tokenize('.')
        String last = props.last()
        List l = props - last
        def currentMap = config
        l?.each {
            if (!currentMap[it]) {
                currentMap[it] = [:]
            }
            currentMap = currentMap[it]
        }
        currentMap["${last}"] = value

        if (name.endsWith('Trigger')) {
            String jobName = name
            CronTrigger trigger = quartzScheduler.getTrigger(jobName, 'topJobsGroup')
            try {
                trigger.setCronExpression(value)
                trigger.startTime = new Date()
                quartzScheduler.rescheduleJob(trigger.name, trigger.group, trigger)
            } catch (Exception exception) {
                println 'jobName=' + jobName
                exception.printStackTrace()
            }
        }

        if (name.startsWith('grails.mail')) {
            // Modify the properties of the mailSender bean
            JavaMailSenderImpl mailSender = ApplicationHolder.application.mainContext.getBean("mailSender")
            if (mailSender) {
                mailSender.host = config.grails.mail.host ?: "localhost"
                mailSender.port = new Integer(config.grails.mail.port ?: 25)
                mailSender.username = config.grails.mail.username ?: null;
                mailSender.password = config.grails.mail.password ?: null;
            }

            // Modify the properties of mailMessage bean
            def mailMessage = ApplicationHolder.application.mainContext.getBean("mailMessage")
            mailMessage.from = (config.grails.mail.fromEmailAddress) ? config.grails.mail.fromEmailAddress : config.grails.mail.defaultEmail.from
        }
    }
}

