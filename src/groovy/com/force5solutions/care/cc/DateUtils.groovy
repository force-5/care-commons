package com.force5solutions.care.cc

class DateUtils {

    public static Integer getQuarter(Date date) {
        Integer quarter = Math.ceil(date.format('M').toInteger() / 3)
        return quarter
    }

    public static Date getFirstDayOfQuarter(Date myDate) {
        Integer quarter = getQuarter(myDate)
        myDate.month = 3 * (quarter - 1)
        myDate.date = 1
        return myDate
    }

    public static Date getLastDayOfQuarter(Date myDate) {
        Integer quarter = getQuarter(myDate)
        myDate.month = 3 * quarter
        myDate.date = 0
        return myDate
    }

    public static Date getLastDayOfNextQuarter(Date myDate) {
        Integer quarter = getQuarter(myDate) + 1
        myDate.month = 3 * quarter
        myDate.date = 0
        return myDate
    }

    public static Long getDifferenceInMinutes(Date date1, Date date2) {
        Long differenceInMinutes = Math.abs(((date1.time - date2.time)/(1000 * 60)))
        return differenceInMinutes
    }

}