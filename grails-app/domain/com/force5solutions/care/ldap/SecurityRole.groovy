package com.force5solutions.care.ldap

class SecurityRole {
    Date dateCreated
    Date lastUpdated
    String name
    String description
    String emails

    static hasMany = [permissionLevels: PermissionLevel]

    static constraints = {
        name(unique: true, blank: false)
        description(blank: true, nullable: true)
        emails(blank: true, nullable: true)
    }

    String toString(){
        name
    }

    boolean equals(o) {
        if (this.is(o)) return true;
        if (!(o.instanceOf(SecurityRole.class))) return false;
        SecurityRole role = (SecurityRole) o;
        return (this.ident() == role.ident())
    }

}
