package com.force5solutions.care.cc

enum PeriodUnit {

    YEARS("Years"),
    MONTHS("Months"),
    WEEKS("Weeks"),
    DAYS("Days"),
    HOURS("Hours"),
    MINUTES("Minutes")

    private final String name;

    PeriodUnit(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }

    static list() {
        [YEARS, MONTHS, WEEKS, DAYS, HOURS, MINUTES]
    }

    static PeriodUnit getPeriodUnit(def s) {
        switch (s) {
            case 'Years': return PeriodUnit.YEARS
            case 'Months': return PeriodUnit.MONTHS
            case 'Weeks': return PeriodUnit.WEEKS
            case 'Days': return PeriodUnit.DAYS
            case 'Hours': return PeriodUnit.HOURS
            case 'Minutes': return PeriodUnit.MINUTES
        }
        return null
    }

    static PeriodUnit get(String name) {
        return (PeriodUnit.list().find {it.name == name})
    }

}
