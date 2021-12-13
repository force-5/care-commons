package com.force5solutions.care.ldap

import javax.naming.Context
import javax.naming.NamingEnumeration
import javax.naming.NamingException
import javax.naming.PartialResultException
import javax.naming.directory.Attribute
import javax.naming.directory.Attributes
import javax.naming.directory.SearchControls
import javax.naming.directory.SearchResult
import javax.naming.ldap.InitialLdapContext
import javax.naming.ldap.LdapContext
import org.codehaus.groovy.grails.commons.ConfigurationHolder

class ActiveDirectoryUtilService {

    public TopUser login(String userName, String password) {
        String domain = ConfigurationHolder.config.top?.activeDirectory?.domain;
        String ldapHost = ConfigurationHolder.config.top?.activeDirectory?.ldapHost;
        String searchBase = ConfigurationHolder.config.top?.activeDirectory?.searchBase;
        String securityAuthentication = ConfigurationHolder.config.top?.activeDirectory?.securityAuthentication;

        TopUser topUser = null;
        List<String> attributeListForUser = ["memberOf"]
        List<String> attributeListForGroup = ["CN"]
        String searchFilter = "(&(objectClass=user)(sAMAccountName=" + userName + "))";

        //Create the search controls
        SearchControls searchCtls = new SearchControls();
        searchCtls.setReturningAttributes((String[]) attributeListForUser.toArray(new String[attributeListForUser.size()]));

        //Specify the search scope
        searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);

        Hashtable env = new Hashtable();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, ldapHost);
        env.put(Context.SECURITY_AUTHENTICATION, securityAuthentication);
        env.put(Context.SECURITY_PRINCIPAL, userName + "@" + domain);
        env.put(Context.SECURITY_CREDENTIALS, password);

        LdapContext ctxGC = null;

        try {
            ctxGC = new InitialLdapContext(env, null);
            //Search objects in GC using filters
            NamingEnumeration answer = ctxGC.search(searchBase, searchFilter, searchCtls);
            topUser = new TopUser(slid: userName, rolesString: "");
            List<String> roles = []
            while (answer.hasMoreElements()) {
                SearchResult sr = (SearchResult) answer.next();
                Attributes attrs = sr.getAttributes();
                if (attrs != null) {
                    NamingEnumeration ne = attrs.getAll();
                    while (ne.hasMore()) {
                        Attribute attr = (Attribute) ne.next();
                        if (attr.getID().equals("memberOf")) {
                            Attribute memberOf = attr;
                            for (int i = 0; i < memberOf.size(); i++) {
                                try {
                                    Attributes atts = ctxGC.getAttributes(memberOf.get(i).toString(), (String[]) attributeListForGroup.toArray(new String[attributeListForUser.size()]));
                                    Attribute att = atts.get("CN");
                                    roles.add(att.get().toString())
                                } catch (PartialResultException e) {
                                    log.warn(e.message)
                                }
                            }
                        }
                    }
                    ne.close();
                }
                topUser.rolesString = roles.join(",")
                log.info "Returning ${topUser}"
                return topUser;
            }
        } catch (NamingException ex) {
            log.error "Exception while authenticating with Active Directory for ${userName}"
            ex.printStackTrace();
        }
        return null;
    }


}
