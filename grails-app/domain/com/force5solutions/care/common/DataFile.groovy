package com.force5solutions.care.common

abstract class DataFile {
    Date dateCreated
    Date lastUpdated

    String fileName
    byte[] bytes
    Long size
}
