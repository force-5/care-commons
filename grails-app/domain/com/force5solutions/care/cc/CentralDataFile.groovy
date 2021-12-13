package com.force5solutions.care.cc

import com.force5solutions.care.common.DataFile

class CentralDataFile extends DataFile {
    static constraints = {
        bytes(maxSize: 5000000)
        dateCreated(nullable: true)
        lastUpdated(nullable: true)
        size(nullable: true)
    }

    def beforeInsert = {
        size = bytes?.size()
    }

    def beforeUpdate= {
        size = bytes?.size()
    }

    CentralDataFile(){}

    CentralDataFile(UploadedFile uploadedFile) {
        fileName = uploadedFile.fileName
        bytes = uploadedFile.bytes
    }

    String toString() {
        return fileName
    }
}
