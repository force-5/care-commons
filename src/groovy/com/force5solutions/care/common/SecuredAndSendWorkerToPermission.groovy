package com.force5solutions.care.common

import com.force5solutions.care.ldap.Permission
import java.lang.annotation.ElementType
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.lang.annotation.Target

@Target([ElementType.FIELD, ElementType.TYPE]) // Annotation is for actions as well as controller so target is field and for class
@Retention(RetentionPolicy.RUNTIME)
@interface SecuredAndSendWorkerToPermission {
    Permission value()

    String[] exclude() default []
}
