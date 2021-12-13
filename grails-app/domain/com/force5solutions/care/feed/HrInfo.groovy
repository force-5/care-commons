package com.force5solutions.care.feed

import org.codehaus.groovy.grails.commons.ConfigurationHolder

class HrInfo {

    Long pernr
    String slid
    String FULL_NAME
    String FIRST_NAME
    String LAST_NAME
    String PERSON_STATUS
    String ORGUNIT_NUM
    String ORGUNIT_DESC
    String workerNumber
    String POSITION_TITLE
    String PERS_AREA_NUM
    String PERS_AREA_DESC
    String PERS_SUBAREA_NUM
    String PERS_SUBAREA_DESC
    String OFFICE_PHONE_NUM
    String CELL_PHONE_NUM
    String PAGER_NUM
    String supervisorSlid
    String SUPV_FULL_NAME
    String SUPVSUPV_SLID_ID
    String SUPVSUPV_FULL_NAME
    Date UPDATE_DT
    Date INSERT_DT

    void setSlid(String slid){
        this.slid = slid?.toUpperCase()
    }

    void setSupervisorSlid(String supervisorSlid){
        this.supervisorSlid = supervisorSlid?.toUpperCase()
    }

    void setSUPVSUPV_SLID_ID(String SUPVSUPV_SLID_ID){
        this.SUPVSUPV_SLID_ID = SUPVSUPV_SLID_ID?.toUpperCase()
    }

    static constraints = {
        pernr(nullable: true)
        slid(nullable: true)
        FULL_NAME(nullable: true)
        FIRST_NAME(nullable: true)
        LAST_NAME(nullable: true)
        PERSON_STATUS(nullable: true)
        ORGUNIT_NUM(nullable: true)
        ORGUNIT_DESC(nullable: true)
        workerNumber(nullable: true)
        POSITION_TITLE(nullable: true)
        PERS_AREA_NUM(nullable: true)
        PERS_AREA_DESC(nullable: true)
        PERS_SUBAREA_NUM(nullable: true)
        PERS_SUBAREA_DESC(nullable: true)
        OFFICE_PHONE_NUM(nullable: true)
        CELL_PHONE_NUM(nullable: true)
        PAGER_NUM(nullable: true)
        supervisorSlid(nullable: true)
        SUPV_FULL_NAME(nullable: true)
        SUPVSUPV_SLID_ID(nullable: true)
        SUPVSUPV_FULL_NAME(nullable: true)
        UPDATE_DT(nullable: true)
        INSERT_DT(nullable: true)
    }

    def beforeInsert = {
        if (ConfigurationHolder.config.containsKey('hrInfoMandatoryFields')) {
            String mandatoryFieldsString = ConfigurationHolder.config.hrInfoMandatoryFields
            List<String> mandatoryFields = mandatoryFieldsString?.tokenize(', ')
            mandatoryFields = mandatoryFields.findAll{it}
            mandatoryFields.each {String mandatoryField ->
                if (!this.getProperty(mandatoryField)) {
                    throw new Exception(mandatoryField + ' cannot be null')
                }
            }
        }
    }

    static mapping = {
        version false
        columns {
            pernr column: 'PERNR'
            slid column: 'SLID_ID'
            supervisorSlid column: 'SUPV_SLID_ID'
            workerNumber column: 'POSITION_NUM'
        }
    }

}
