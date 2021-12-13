package com.force5solutions.care.cc

class CcOrigin {

    String name

    static constraints = {
        name(unique: true)
    }

    String toString(){
        return name
    }
    boolean equals(o) {
        if (this.is(o)) return true;
        if (!(o.instanceOf(CcOrigin.class))) return false;
        CcOrigin g = (CcOrigin) o;
        return (this.ident() == g.ident())
    }


}
