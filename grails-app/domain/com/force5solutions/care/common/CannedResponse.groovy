package com.force5solutions.care.common

class CannedResponse {

    String taskDescription
    String response
    String responseDescription
    Integer priority = 99

    static constraints = {
    }

    String toString() {
        return "${taskDescription} - ${response}"
    }
}
