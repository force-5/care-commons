package com.force5solutions.care.cc

class Course {

    String name
    String number
    Date startDate
    Date endDate

    static constraints = {
        name()
        number()
        startDate(nullable: true)
        endDate(nullable: true)
    }

    String toString() {
        return name
    }

    boolean equals(o) {
        if (this.is(o)) return true;
        if (!(o.instanceOf(Course.class))) return false;
        Course course = (Course) o;
        return (this.ident() == course.ident())
    }
}
