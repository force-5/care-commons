package com.force5solutions.care.common

import java.lang.annotation.Target
import java.lang.annotation.Retention
import java.lang.annotation.ElementType
import java.lang.annotation.RetentionPolicy
import com.force5solutions.care.ldap.Permission

@Target([ElementType.FIELD, ElementType.TYPE]) // Annotation is for actions as well as controller so target is field and for class
@Retention(RetentionPolicy.RUNTIME)
@interface Secured {
    Permission value()

    String[] exclude() default []
}
