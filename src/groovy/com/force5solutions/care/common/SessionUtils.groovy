package com.force5solutions.care.common

import org.springframework.web.context.request.RequestContextHolder

class SessionUtils {
    public static session
    public static request
    // method for setting session during tests

    public static void setSession(def mockSession) {
        session = mockSession
    }

    // method for setting request during tests

    public static void setRequest(def mockRequest) {
        request = mockRequest
    }

    public static def getSession() {
        if (session) {
            return session
        }
        def currentSession
        try {
            currentSession = RequestContextHolder?.currentRequestAttributes()?.getSession()
        } catch (Throwable t) {
        }
        return currentSession
    }

    public static def getRequest() {
        if (request) {
            return request
        }
        def currentRequest
        try {
            currentRequest = RequestContextHolder?.currentRequestAttributes()?.getCurrentRequest()
        } catch (Throwable t) {
        }
        return currentRequest
    }
}
