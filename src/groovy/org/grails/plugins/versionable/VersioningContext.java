package org.grails.plugins.versionable;

import java.util.UUID;

public class VersioningContext {
    static ThreadLocal<String> currentContext = new ThreadLocal<String>();

    public static String get() {
        String rtn = currentContext.get();
        if (rtn == null) {
            String uuid = UUID.randomUUID().toString();
            currentContext.set(uuid);
            rtn = uuid;
        }
        return rtn;
    }

    public static void set(String contextId) {
          currentContext.set(contextId);
    }
}