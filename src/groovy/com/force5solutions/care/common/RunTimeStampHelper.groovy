package com.force5solutions.care.common

class RunTimeStampHelper {
    Long startTime

    RunTimeStampHelper(){
      startTime = new Date().time
    }

    public String stamp(){
        return "TIME TAKEN TO EXECUTE: ${(new Date().time - startTime)}(milliseconds)"
    }

}
