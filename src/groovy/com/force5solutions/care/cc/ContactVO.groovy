package com.force5solutions.care.cc

class ContactVO {
    Long id
    String title

    ContactVO(def id, def title) {
        this.id = id
        this.title = title
    }

    ContactVO(obj) {
        id = obj.id
        title = "${obj.lastName?:''}, ${obj.firstName?:''} ${obj.middleName?:''}"
    }

    String toString() {
        title
    }
}