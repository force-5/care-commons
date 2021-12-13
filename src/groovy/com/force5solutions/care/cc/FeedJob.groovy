package com.force5solutions.care.cc

import org.codehaus.groovy.grails.commons.ConfigurationHolder

abstract class FeedJob {

    def config = ConfigurationHolder.config
    String configKeyName

    def execute() {
        if (config."${configKeyName}".toString().equalsIgnoreCase("true")) {
            executeFeedFromDbSource()
        } else {
            executeFeedFromFileSource()
        }
    }

    abstract void executeFeedFromFileSource()

    abstract void executeFeedFromDbSource()
}
