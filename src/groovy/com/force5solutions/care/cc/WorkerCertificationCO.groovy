package com.force5solutions.care.cc

import java.text.SimpleDateFormat
import org.springframework.web.multipart.commons.CommonsMultipartFile

class WorkerCertificationCO {
    String provider;
    String dateCompleted_value;
    String testProvider;
    long id;
    long certificationId;
    String testDate;
    String trainingDate;
    String testStatus;
    String trainingStatus;
    long workerCertificationId
    String trainingProvider;
    String subType;
    Collection<Long> existingAffidavitIds;
    Collection<UploadedFile> affidavits = []

    String toString() {
        String s = "" + certificationId + ":" + dateCompleted + ":" + testProvider + ":" + id + ":" + testDateAsDate + ":" +
                testStatus + ":" + trainingStatus + ":" + workerCertificationId + ":" + trainingProvider + ":" + subType + ":" + existingAffidavitIds + ":" + affidavits.size();
        return s;
    }

    Date getDateCompleted() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy")
        if (dateCompleted_value?.length() > 0) {
            return dateFormat.parse(dateCompleted_value)
        } else {
            return null;
        }
    }

    Date getTestDateAsDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy")
        if (testDate?.length() > 0) {
            return dateFormat.parse(testDate)
        } else {
            return null;
        }
    }

    Date getTrainingDateAsDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy")
        if (trainingDate?.length() > 0) {
            return dateFormat.parse(trainingDate)
        } else {
            return null;
        }
    }


    void populateExistingAffidavitIds(def params) {
        existingAffidavitIds = [params.existingAffidavitIds].flatten()*.toLong()
    }

    CertificationStatus getTestStatusObject() {
        CertificationStatus testStatus1;
        if (testStatus || testProvider) {
            testStatus1 = new CertificationStatus()
            testStatus1.status = testStatus
            testStatus1.date = getTestDateAsDate()
            testStatus1.provider = testProvider
            testStatus1.s()
        }
        return testStatus1;
    }

    CertificationStatus getTrainingStatusObject() {
        CertificationStatus trainingStatus1;
        if (trainingStatus || trainingProvider) {
            trainingStatus1 = new CertificationStatus()
            trainingStatus1.status = trainingStatus
            trainingStatus1.date = getTrainingDateAsDate();
            trainingStatus1.provider = trainingProvider;
            trainingStatus1.s();
        }
        return trainingStatus1;
    }

    void populateAffidavits(def params) {
        Map files = params.findAll {entry ->
            entry.key.startsWith("multiFiles") &&
                    entry.value.class.name == "org.springframework.web.multipart.commons.CommonsMultipartFile"
        }
        files.each {
            CommonsMultipartFile file = params."$it.key"
            if (!file.isEmpty()) {
                UploadedFile affidavit1 = new UploadedFile(fileName: file.getOriginalFilename(), bytes: file.getBytes());
                affidavits << affidavit1;
            }
        }
    }
}

