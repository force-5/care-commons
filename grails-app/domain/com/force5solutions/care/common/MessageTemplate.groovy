package com.force5solutions.care.common

import com.force5solutions.care.cc.TransportType
import groovy.text.SimpleTemplateEngine
import groovy.text.Template
import java.util.regex.Matcher
import java.util.regex.Pattern

abstract class MessageTemplate {
    private static final long serialVersionUID = 1L
    Date dateCreated
    Date lastUpdated
    String name
    TransportType transportType
    String subjectTemplate
    String bodyTemplate

    String getBody(Map parameters = [:]) {
        return convertTemplateToBody(bodyTemplate, parameters)
    }

    String getSubject(Map parameters = [:]) {
        return convertTemplateToBody(subjectTemplate, parameters)
    }

    private String convertTemplateToBody(String messageTemplate, Map parameters) {
        Pattern pattern = Pattern.compile('''###(.*?)###''')
        Matcher matcher = pattern.matcher(messageTemplate)
        String updatedText = ''
        CustomTag customTag
        SimpleTemplateEngine engine = new SimpleTemplateEngine()
        Template template
        String customTagValue = ''

        while (matcher.find()) {
            updatedText = matcher.group()
            CustomTag.withSession {
                if (CustomTag.countByDisplayValue(updatedText)) {
                    customTag = CustomTag.findByDisplayValue(updatedText)
                    template = engine.createTemplate(customTag.value)
                    try {
                        customTagValue = template.make(parameters).toString()
                        messageTemplate = messageTemplate.replace(updatedText, customTagValue)
                    } catch (MissingPropertyException e) {
                        StringWriter sw = new StringWriter();
                        PrintWriter pw = new PrintWriter(sw);
                        e.printStackTrace(pw);
                        e.printStackTrace()
                        log.error(e)
                    } catch(Throwable t){
                        t.printStackTrace()
                    }
                }
            }
        }
        return messageTemplate
    }

}