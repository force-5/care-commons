import org.codehaus.groovy.grails.commons.GrailsApplication
import grails.util.GrailsUtil
import org.codehaus.groovy.grails.commons.ConfigurationHolder
import org.grails.plugins.versionable.VersioningContext

class CommonFilters {

    def filters = {
        debug(controller: '*', action: '*') {
            before = {
                VersioningContext.set(UUID.randomUUID().toString())
                if (GrailsUtil.environment != GrailsApplication.ENV_PRODUCTION) {
                    log.debug("GrailsAccessLog:${new Date()}:${params}");
                }
            }
        }

        verifyUserIsLoggedIn(controller: '*', action: '*') {
            before = {
                trimParameters(params)
                changeSlidToUpperCase(params)
                if ((!session.loggedUser) && !(params.controller in ['login', 'accessRequest', 'dashboard', 'chart', 'tvDashboard'])) {
                    if (!((params.controller == 'accessRequest') && !(params.action in ['populatePopup', 'index']))) {
                        if (!((params.controller == 'dashboard') && !(params.action in ['index']))) {
                            if (!((params.controller == 'centralWorkflowTask') && (params.action in ['confirm']))) {
                                if (!params.targetUri) {
                                    String targetUri = request.forwardURI.toString() - request.contextPath.toString()
                                    if (!(targetUri == null || targetUri == "/"))
                                        params.targetUri = targetUri
                                }
                                redirect(controller: 'login', action: 'index', params: params)
                                return false
                            }
                        }
                    }
                }
            }
        }

        superAdminPermissions(controller: 'console|topUser|util|configProperty', action: '*') {
            before = {
                List<String> permittedSlids = ConfigurationHolder.config.superAdminSlids.toString()?.tokenize(', ')?.findAll {it}
                if (session.loggedUser && !(permittedSlids && (session.loggedUser in permittedSlids*.toUpperCase()))) {
                    render(view: "/permissionDenied")
                    return false
                }
            }
        }

    }


    void trimParameters(def params) {
        params?.each {String key, value ->
            try {
                if (value?.getClass() == String) {
                    params[key] = value.trim()
                }
            } catch (Throwable t) {
                t.printStackTrace()
            }
        }
    }

    void changeSlidToUpperCase(def params) {
        params?.each {String key, value ->
            try {
                if ((key.length() > 3) && (value?.getClass() == String) && (key.substring(key.length() - 4).equalsIgnoreCase('slid'))) {
                    params[key] = value?.toUpperCase()
                }
            } catch (Throwable t) {
                t.printStackTrace()
            }
        }
    }


}
