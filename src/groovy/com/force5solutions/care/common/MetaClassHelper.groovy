package com.force5solutions.care.common

import java.text.SimpleDateFormat
import javax.servlet.http.HttpServletRequest
import org.apache.commons.logging.LogFactory
import org.codehaus.groovy.grails.commons.ConfigurationHolder

class MetaClassHelper {

    static config = ConfigurationHolder.config
    private static final log = LogFactory.getLog(this)
    static void enrichClasses() {
        Collection.metaClass.addAllNotNull {Collection collection ->
            collection - null
        }

        HttpServletRequest.metaClass.isXhr = {->
            'XMLHttpRequest' == delegate.getHeader('X-Requested-With')
        }

        // Inject the helper s() method
        Object.metaClass.s = {
            def o = delegate.save(flush: true, failOnError: true)
            if (!o) {
                delegate.errors.allErrors.each {
                    log.error it
                }
            }
            o
        }

        // Inject the helper trimLength() method
        String.metaClass.trimLength = {Integer stringLength ->
            String trimString = delegate?.toString()
            if (stringLength && (trimString?.length() > stringLength)) {
                trimString = trimString.substring(0, stringLength - 3)
                trimString = trimString.substring(0, (trimString.lastIndexOf(" ") > trimString.lastIndexOf(".")) ? trimString.lastIndexOf(" ") : trimString.lastIndexOf(".")) + '...'
            }
            return trimString
        }

        Date.metaClass.myFormat = {
            new SimpleDateFormat('MM-dd-yyyy').format(delegate)
        }
        Date.metaClass.myDateTimeFormat = {
            new SimpleDateFormat('MM/dd/yyyy hh:mm a').format(delegate)
        }
    }
}
