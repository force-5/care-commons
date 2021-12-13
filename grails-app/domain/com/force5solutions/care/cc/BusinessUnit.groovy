package com.force5solutions.care.cc

class BusinessUnit {
    String name

    String toString(){
        return name
    }

    static constraints = {
        name(unique: true)
    }
}
