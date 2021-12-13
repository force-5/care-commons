package com.force5solutions.care.common

class CareConstants {

    public static final String CAREUSER = "CAREUSER"
    public static final String CAREEDITOR = "CAREEDITOR"
    public static final String CAREADMIN = "CAREADMIN"
    public static final String CMNADMIN = "CMNADMIN"
    public static final String PROVISIONER = "PROVISIONER"
    public static final String DEPROVISIONER = "DEPROVISIONER"

    public static final String TEST_EMAIL = "care.force5@gmail.com"
    public static final String BCC_ALERT_RECIPIENT = "care.force5+bcc@gmail.com"

    public static final String CENTRAL_SYSTEM = "Central System"

    public static final String LOCATION_TYPE_CARE_SYSTEM = "Central System"
    public static final String LOCATION_TYPE_COMPANY = "Company"
    public static final String LOCATION_TYPE_BUSINESS_UNIT = "Business Unit"
    public static final String LOCATION_TYPE_CRITICAL_PHYSICAL_LOCATION = "Critical Physical Location"
    public static final String LOCATION_TYPE_CRITICAL_CYBER_LOCATION = "Critical Cyber Location"

    //Entitlement Role Access Statuses
    public static final String PENDING_ACCESS_APPROVAL = "Pending Access Approval"
    public static final String PENDING_ACCESS_REVOCATION = "Pending Access Revocation"
    public static final String PENDING_TERMINATION = "Pending Termination"
    public static final String REJECTED = "Rejected"
    public static final String ACTIVE = "Active"
    public static final String REVOKED = "Revoked"
    public static final String TERMINATED_FOR_CAUSE = "Terminated for Cause"
    public static final String ERROR = "Error"
    public static final String TIME_OUT = "Time out"

    public static final String PICTURE_PERFECT_LOCATION_FILE_DELIMITER_TOKEN = "|"
    public static final String PICTURE_PERFECT_WORKER_LOCATION_FILE_DELIMITER_TOKEN = ","
    public static final String PICTURE_PERFECT_PRA_FEED_FILE_DELIMITER_TOKEN = ","
    public static final String PICTURE_PERFECT_LOCATION_LINE_ITEM_ENTITY_TYPE = "PICTURE PERFECT LOCATION LINE ITEM"
    public static final String PICTURE_PERFECT_LOCATION_ACCESS_LINE_ITEM_ENTITY_TYPE = "PICTURE PERFECT LOCATION ACCESS LINE ITEM"
    public static final String PICTURE_PERFECT_FILE_LOCATION = "LOCATION"
    public static final String PICTURE_PERFECT_FILE_WORKER_LOCATION = "WORKER_LOCATION"

    public static final String CENTRAL_SYSTEM_USER_ID = "Central System"
    public static final String APS_SYSTEM_USER_ID = "APS System"
    public static final String TIM_SYSTEM_USER_ID = "TIM System"
    public static final String CARE_SYSTEM_LOCATION = "Central System"
    public static final String CARE_SYSTEM_COMPANY_LOCATION = "Company 1"
    public static final String CARE_SYSTEM_BUSINESS_UNIT_LOCATION = "Business Unit 1"

    public static final Long NOT_AUTHORIZED_PERMISSION_LEVEL = 0
    public static final Long UNRESTRICTED_ACCESS_PERMISSION_LEVEL = 1
    public static final Long ACCESS_IF_BUR_PERMISSION_LEVEL = 3
    public static final Long ACCESS_IF_EMPLOYEE_PERMISSION_LEVEL = 5
    public static final Long ACCESS_IF_EMPLOYEE_SUPERVISOR_PERMISSION_LEVEL = 7
    public static final Long ACCESS_IF_BUR_OWNS_PERMISSION_LEVEL = 13
    public static final Long ACCESS_IF_SPONSOR_HAS_CERTIFICATIONS_PERMISSION_LEVEL = 17
    public static final Long ACCESS_IF_EMPLOYEE_SUPERVISOR_OWNS_PERMISSION_LEVEL = 19
    public static final Long ACCESS_IF_ROLE_OWNER_OWNS_PERMISSION_LEVEL = 23
    public static final Long ACCESS_IF_GATEKEEPER_PERMISSION_LEVEL = 31
    public static final Long ACCESS_IF_GATEKEEPER_OWNS_PERMISSION_LEVEL = 37
    public static final Long ACCESS_IF_SELF_PERMISSION_LEVEL = 41

    public static final String PHYSICAL_CIP = "Physical CIP"
    public static final String CYBER_CIP = "Cyber CIP"
    public static final String BOTH_CIP = "Both"

    public static final String ENTITLEMENT_POLICY_PHYSICAL = "Physical"
    public static final String ENTITLEMENT_POLICY_CYBER = "Cyber"
    public static final String ENTITLEMENT_POLICY_CRITICAL_PHYSICAL_ENTITLEMENT = "Critical Physical Entitlement"
    public static final String ENTITLEMENT_POLICY_CRITICAL_CYBER_ENTITLEMENT = "Critical Cyber Entitlement"

    //Entitlement Access Statuses

    public static final String PICTURE_PERFECT_FEED = "Picture Perfect"
    public static final String SAP_EMPLOYEE_FEED = "SAP Employee"
    public static final String SAP_PRA_FEED = "SAP PRA"

    public static final String PICTURE_PERFECT_ENTITLEMENT_FILE_DELIMITER_TOKEN = "|"
    public static final String PICTURE_PERFECT_WORKER_ENTITLEMENT_FILE_DELIMITER_TOKEN = ","
    public static final String PICTURE_PERFECT_ENTITLEMENT_LINE_ITEM_ENTITY_TYPE = "PICTURE PERFECT ENTITLEMENT LINE ITEM"
    public static final String PICTURE_PERFECT_ENTITLEMENT_ACCESS_LINE_ITEM_ENTITY_TYPE = "PICTURE PERFECT ENTITLEMENT ACCESS LINE ITEM"
    public static final String PICTURE_PERFECT_FILE_ENTITLEMENT = "ENTITLEMENT"
    public static final String PICTURE_PERFECT_FILE_WORKER_ENTITLEMENT = "WORKER_ENTITLEMENT"

    //TODO: Refactor name to constants to start with APS_WORKFLOW_TASK_TEMPLATE / CENTRAL_WORKFLOW_TASK_TEMPLATE
    public static final String CENTRAL_REVOKE_IN_24_HOURS_COMPLETE_TASK_TEMPLATE = "REVOKE_IN_24_HOURS_COMPLETE_TASK_TEMPLATE"
    public static final String CENTRAL_REVOKE_IN_24_HOURS_COMPLETE_TASK_TEMPLATE_FOR_CONTRACTOR = "REVOKE_IN_24_HOURS_COMPLETE_TASK_TEMPLATE_FOR_CONTRACTOR"
    public static final String CENTRAL_REVOKE_IN_7_DAYS_COMPLETE_TASK_TEMPLATE = "REVOKE_IN_7_DAYS_COMPLETE_TASK_TEMPLATE"
    public static final String CENTRAL_REVOKE_IN_7_DAYS_COMPLETE_TASK_TEMPLATE_FOR_CONTRACTOR = "REVOKE_IN_7_DAYS_COMPLETE_TASK_TEMPLATE_FOR_CONTRACTOR"
    public static final String WORKFLOW_TASK_TEMPLATE_REVOKE_IN_24_HOURS = "REVOKE_24_HOURS_APS_SYSTEM_TASK_TEMPLATE"
    public static final String WORKFLOW_TASK_TEMPLATE_REVOKE_IN_7_DAYS = "REVOKE_7_DAYS_APS_SYSTEM_TASK_TEMPLATE"
    public static final String REVOKE_WORKFLOW_TASK_IN_24_HOURS = "REVOKE_WORKFLOW_TASK_IN_24_HOURS"
    public static final String REVOKE_WORKFLOW_TASK_IN_7_DAYS = "REVOKE_WORKFLOW_TASK_IN_7_DAYS"
    public static final String WORKFLOW_TASK_TEMPLATE_REVOKE_ESCALATION = "REVOKE_ESCALATION_TEMPLATE"
    public static final String WORKFLOW_TASK_TEMPLATE_INITIAL_TASK = "INITIAL_TASK_TEMPLATE"
    public static final String WORKFLOW_TASK_TEMPLATE_CENTRAL_SYSTEM_TASK = "CENTRAL_SYSTEM_TASK_TEMPLATE"
    public static final String WORKFLOW_TASK_TEMPLATE_APS_SYSTEM_TASK = "APS_SYSTEM_TASK_TEMPLATE"
    public static final String WORKFLOW_TASK_TEMPLATE_TIM_SYSTEM_TASK = "TIM_SYSTEM_TASK_TEMPLATE"
    public static final String WORKFLOW_TASK_TEMPLATE_ENTITLEMENT_POLICY_APPROVAL = "ENTITLEMENT_POLICY_APPROVAL_TEMPLATE"
    public static final String WORKFLOW_TASK_TEMPLATE_ENTITLEMENT_POLICY_ESCALATION = "ENTITLEMENT_POLICY_ESCALATION_TEMPLATE"
    public static final String WORKFLOW_TASK_TEMPLATE_ACCESS_APPROVAL_BY_SUPERVISOR = "ACCESS_APPROVAL_BY_SUPERVISOR"
    public static final String WORKFLOW_TASK_TEMPLATE_ACCESS_APPROVAL_BY_BUSINESS_UNIT_REQUESTER = "ACCESS_APPROVAL_BY_BUSINESS_UNIT_REQUESTER"
    public static final String WORKFLOW_TASK_TEMPLATE_ACCESS_APPROVAL_BY_GATEKEEPER = "ACCESS_APPROVAL_BY_GATEKEEPER"
    public static final String WORKFLOW_TASK_TEMPLATE_REVOKE_APPROVAL_BY_GATEKEEPER = "REVOKE_APPROVAL_BY_GATEKEEPER"
    public static final String WORKFLOW_TASK_TEMPLATE_CERTIFICATION_EXPIRY_NOTIFICATION_GENERAL = "CERTIFICATION_EXPIRY_NOTIFICATION_GENERAL_TEMPLATE"
    public static final String WORKFLOW_TASK_TEMPLATE_CERTIFICATION_EXPIRY_NOTIFICATION_GENERAL_FOR_CONTRACTOR = "WORKFLOW_TASK_TEMPLATE_CERTIFICATION_EXPIRY_NOTIFICATION_GENERAL_FOR_CONTRACTOR"
    public static final String WORKFLOW_TASK_TEMPLATE_CERTIFICATION_EXPIRY_NOTIFICATION_FINAL = "CERTIFICATION_EXPIRY_NOTIFICATION_FINAL_TEMPLATE"
    public static final String WORKFLOW_TASK_TEMPLATE_CERTIFICATION_EXPIRY_NOTIFICATION_FINAL_FOR_CONTRACTOR = "WORKFLOW_TASK_TEMPLATE_CERTIFICATION_EXPIRY_NOTIFICATION_FINAL_FOR_CONTRACTOR"
    public static final String WORKFLOW_TASK_TEMPLATE_ACCESS_CONFIRM_BY_PROVISIONER = "ACCESS_CONFIRM_BY_PROVISIONER"
    public static final String WORKFLOW_TASK_TEMPLATE_REVOKE_CONFIRM_BY_PROVISIONER = "REVOKE_CONFIRM_BY_PROVISIONER"
    public static final String WORKFLOW_TASK_TEMPLATE_TERMINATE_CONFIRM_BY_PROVISIONER = "TERMINATE_CONFIRM_BY_PROVISIONER"
    public static final String WORKFLOW_TASK_TEMPLATE_ACCESS_VERIFICATION = "ACCESS_VERIFICATION"
    public static final String WORKFLOW_TASK_TEMPLATE_ESCALATED_ACCESS_VERIFICATION = "ESCALATED_ACCESS_VERIFICATION"
    public static final String WORKFLOW_TASK_TEMPLATE_ESCALATED_ACCESS_VERIFICATION_NOTIFICATION_TO_ORIGINAL_SUPERVISOR = "ESCALATED_ACCESS_VERIFICATION_NOTIFICATION_TO_ORIGINAL_SUPERVISOR"
    public static final String WORKFLOW_TASK_TEMPLATE_ACCESS_VERIFICATION_NOTICE = "ACCESS_VERIFICATION_NOTICE"
    public static final String WORKFLOW_TASK_TEMPLATE_ADD_ROLE = "ADD_ROLE"
    public static final String WORKFLOW_TASK_TEMPLATE_UPDATE_ROLE = "UPDATE_ROLE"
    public static final String WORKFLOW_TASK_TEMPLATE_ADD_ENTITLEMENT = "ADD_ENTITLEMENT"
    public static final String WORKFLOW_TASK_TEMPLATE_ACCOUNT_PASSWORD_CHANGE = "ACCOUNT_PASSWORD_CHANGE"
    public static final String WORKFLOW_TASK_TEMPLATE_UPDATE_ENTITLEMENT = "UPDATE_ENTITLEMENT"
    public static final String WORKFLOW_TASK_TEMPLATE_UPDATE_ENTITLEMENT_FEED_EXCEPTION = "UPDATE_ENTITLEMENT_FEED_EXCEPTION"
    public static final String WORKFLOW_TASK_TEMPLATE_CREATE_ENTITLEMENT_FEED_EXCEPTION = "CREATE_ENTITLEMENT_FEED_EXCEPTION"
    public static final String WORKFLOW_TASK_TEMPLATE_APS_ACCESS_VERIFICATION = "APS_ACCESS_VERIFICATION"
    public static final String SEND_ACCESS_REQUEST_CONFIRMATION_EMAIL_TO_EMPLOYEE_CENTRAL_SYSTEM_TASK_TEMPLATE = "SEND_ACCESS_REQUEST_CONFIRMATION_EMAIL_TO_EMPLOYEE_CENTRAL_SYSTEM_TASK_TEMPLATE"
    public static final String SEND_ACCESS_REQUEST_CONFIRMATION_EMAIL_TO_CONTRACTOR_CENTRAL_SYSTEM_TASK_TEMPLATE = "SEND_ACCESS_REQUEST_CONFIRMATION_EMAIL_TO_CONTRACTOR_CENTRAL_SYSTEM_TASK_TEMPLATE"
    public static final String SUPERVISOR_REJECT_NOTIFICATION_TO_EMPLOYEE_CENTRAL_SYSTEM_TASK_TEMPLATE = "SUPERVISOR_REJECT_NOTIFICATION_TO_EMPLOYEE_CENTRAL_SYSTEM_TASK_TEMPLATE"
    public static final String SUPERVISOR_REJECT_NOTIFICATION_TO_CONTRACTOR_CENTRAL_SYSTEM_TASK_TEMPLATE = "SUPERVISOR_REJECT_NOTIFICATION_TO_CONTRACTOR_CENTRAL_SYSTEM_TASK_TEMPLATE"
    public static final String TIMEOUT_NOTIFICATION_CENTRAL_SYSTEM_TASK_TEMPLATE = "TIMEOUT_NOTIFICATION_CENTRAL_SYSTEM_TASK_TEMPLATE"
    public static final String TIMEOUT_NOTIFICATION_CENTRAL_SYSTEM_TASK_TEMPLATE_FOR_CONTRACTOR = "TIMEOUT_NOTIFICATION_CENTRAL_SYSTEM_TASK_TEMPLATE_FOR_CONTRACTOR"
    public static final String MISSING_CERTIFICATION_DETAILS_EMAIL_TO_EMPLOYEE_CENTRAL_SYSTEM_TASK_TEMPLATE = "MISSING_CERTIFICATION_DETAILS_EMAIL_TO_EMPLOYEE_CENTRAL_SYSTEM_TASK_TEMPLATE"
    public static final String MISSING_CERTIFICATION_DETAILS_EMAIL_TO_CONTRACTOR_CENTRAL_SYSTEM_TASK_TEMPLATE = "MISSING_CERTIFICATION_DETAILS_EMAIL_TO_CONTRACTOR_CENTRAL_SYSTEM_TASK_TEMPLATE"

    public static final String CONTRACTOR_CLOUD_WORKFLOW_TASK_TEMPLATE_ADD_CONTRACTOR_CSR = "CONTRACTOR_CLOUD_WORKFLOW_TASK_TEMPLATE_ADD_CONTRACTOR_CSR"
    public static final String CONTRACTOR_CLOUD_WORKFLOW_TASK_TEMPLATE_ADD_CONTRACTOR_WELCOME_EMAIL = "CONTRACTOR_CLOUD_WORKFLOW_TASK_TEMPLATE_ADD_CONTRACTOR_WELCOME_EMAIL"
    public static final String CONTRACTOR_CLOUD_WORKFLOW_TASK_TEMPLATE_ADD_WORKER_CERTIFICATION_CSR = "CONTRACTOR_CLOUD_WORKFLOW_TASK_TEMPLATE_ADD_WORKER_CERTIFICATION_CSR"
    public static final String CONTRACTOR_CLOUD_WORKFLOW_TASK_TEMPLATE_ADD_WORKER_CERTIFICATION_NOTIFICATION = "CONTRACTOR_CLOUD_WORKFLOW_TASK_TEMPLATE_ADD_WORKER_CERTIFICATION_NOTIFICATION"

    public static final String APS_REVOKE_24_HOURS_PROVISIONER_TASK_TEMPLATE = "APS_REVOKE_24_HOURS_PROVISIONER_TASK_TEMPLATE"
    public static final String APS_REVOKE_24_HOURS_ESCALATE_VP_TASK_TEMPLATE = "APS_REVOKE_24_HOURS_ESCALATE_VP_TASK_TEMPLATE"
    public static final String APS_REVOKE_24_HOURS_ESCALATE_DIRECTOR_TASK_TEMPLATE = "APS_REVOKE_24_HOURS_ESCALATE_DIRECTOR_TASK_TEMPLATE"
    public static final String APS_REVOKE_24_HOURS_ESCALATE_COMPLIANCE_LEAD_TASK_TEMPLATE = "APS_REVOKE_24_HOURS_ESCALATE_COMPLIANCE_LEAD_TASK_TEMPLATE"
    public static final String APS_REVOKE_24_HOURS_ESCALATE_GM_TASK_TEMPLATE = "APS_REVOKE_24_HOURS_ESCALATE_GM_TASK_TEMPLATE"

    public static final String APS_REVOKE_7_DAYS_PROVISIONER_TASK_TEMPLATE = "APS_REVOKE_7_DAYS_PROVISIONER_TASK_TEMPLATE"
    public static final String APS_REVOKE_7_DAYS_ESCALATE_VP_TASK_TEMPLATE = "APS_REVOKE_7_DAYS_ESCALATE_VP_TASK_TEMPLATE"
    public static final String APS_REVOKE_7_DAYS_ESCALATE_DIRECTOR_TASK_TEMPLATE = "APS_REVOKE_7_DAYS_ESCALATE_DIRECTOR_TASK_TEMPLATE"
    public static final String APS_REVOKE_7_DAYS_ESCALATE_COMPLIANCE_LEAD_TASK_TEMPLATE = "APS_REVOKE_7_DAYS_ESCALATE_COMPLIANCE_LEAD_TASK_TEMPLATE"
    public static final String APS_REVOKE_7_DAYS_ESCALATE_GM_TASK_TEMPLATE = "APS_REVOKE_7_DAYS_ESCALATE_GM_TASK_TEMPLATE"

    public static final String CENTRAL_MESSAGE_TEMPLATE_ENTITLEMENT_POLICY_CREATION = 'Entitlement Policy Creation'
    public static final String CENTRAL_MESSAGE_TEMPLATE_ACCESS_REQUEST = 'Access request template'
    public static final String CENTRAL_MESSAGE_TEMPLATE_ACCESS_REQUEST_FOR_EMPLOYEE = 'Access request template for employee'
    public static final String CENTRAL_MESSAGE_TEMPLATE_ACCESS_REQUEST_FOR_CONTRACTOR = 'Access request template for contractor'
    public static final String CENTRAL_MESSAGE_TEMPLATE_ACCESS_REQUEST_SUPERVISOR_REJECT_FOR_EMPLOYEE = 'Access request template supervisor reject for employee'
    public static final String CENTRAL_MESSAGE_TEMPLATE_ACCESS_REQUEST_SUPERVISOR_REJECT_FOR_CONTRACTOR = 'Access request template supervisor reject for contractor'
    public static final String CENTRAL_MESSAGE_TEMPLATE_ACCESS_REQUEST_TIME_OUT_NOTIFICATION = 'Access request time out notification'
    public static final String CENTRAL_MESSAGE_TEMPLATE_ACCESS_REQUEST_TIME_OUT_NOTIFICATION_FOR_CONTRACTOR = 'Access request time out notification for contractor'
    public static final String CENTRAL_MESSAGE_TEMPLATE_ACCESS_REQUEST_MISSING_CERTIFICATIONS_EMAIL_TO_EMPLOYEE = 'Access Request missing certification email to employee'
    public static final String CENTRAL_MESSAGE_TEMPLATE_ACCESS_REQUEST_MISSING_CERTIFICATIONS_EMAIL_TO_CONTRACTOR = 'Access Request missing certification email to contractor'
    public static final String CENTRAL_MESSAGE_TEMPLATE_CERTIFICATION_EXPIRATION_GENERAL = 'Certification expiration general template'
    public static final String CENTRAL_MESSAGE_TEMPLATE_CERTIFICATION_EXPIRATION_FINAL = 'Certification expiration final template'
    public static final String CENTRAL_MESSAGE_TEMPLATE_ACCESS_VERIFICATION = 'Access verification template'
    public static final String CENTRAL_MESSAGE_TEMPLATE_ACCESS_VERIFICATION_ESCALATION = 'Access verification escalation template'
    public static final String CENTRAL_MESSAGE_TEMPLATE_ACCESS_VERIFICATION_ESCALATION_NOTIFICATION_TO_ORIGINAL_SUPERVISOR = 'Access verification escalation notification to original supervisor template'
    public static final String CENTRAL_MESSAGE_TEMPLATE_ACCESS_VERIFICATION_NOTICE = 'Access verification notice template'
    public static final String CENTRAL_MESSAGE_TEMPLATE_24H_REVOCATION_COMPLETE = '24 Hours revocation complete'
    public static final String CENTRAL_MESSAGE_TEMPLATE_7D_REVOCATION_COMPLETE = '7 Days revocation complete'

    public static final String CONTRACTOR_CLOUD_MESSAGE_TEMPLATE_ADD_CONTRACTOR_EMAIL_CSR = 'CONTRACTOR_CLOUD_MESSAGE_TEMPLATE_ADD_CONTRACTOR_EMAIL_CSR'
    public static final String CONTRACTOR_CLOUD_MESSAGE_TEMPLATE_ADD_CONTRACTOR_EMAIL_WELCOME_EMAIL = 'CONTRACTOR_CLOUD_MESSAGE_TEMPLATE_ADD_CONTRACTOR_EMAIL_WELCOME_EMAIL'
    public static final String CONTRACTOR_CLOUD_MESSAGE_TEMPLATE_ADD_WORKER_CERTIFICATION_EMAIL_CSR = 'CONTRACTOR_CLOUD_MESSAGE_TEMPLATE_ADD_WORKER_CERTIFICATION_EMAIL_CSR'
    public static final String CONTRACTOR_CLOUD_MESSAGE_TEMPLATE_ADD_WORKER_CERTIFICATION_EMAIL_NOTIFICATION = 'CONTRACTOR_CLOUD_MESSAGE_TEMPLATE_ADD_WORKER_CERTIFICATION_EMAIL_NOTIFICATION'

    public static final String APS_MESSAGE_TEMPLATE_ENTITLEMENT_ROLE_CREATION = 'Entitlement Role Creation'
    public static final String APS_MESSAGE_TEMPLATE_ENTITLEMENT_ROLE_UPDATION = 'Entitlement Role Updation'
    public static final String APS_MESSAGE_TEMPLATE_ENTITLEMENT_CREATION = 'Entitlement Creation'
    public static final String APS_MESSAGE_TEMPLATE_ACCOUNT_PASSWORD_CHANGE = 'Account Password Change'
    public static final String APS_MESSAGE_TEMPLATE_ENTITLEMENT_UPDATION = 'Entitlement Updation'
    public static final String APS_MESSAGE_TEMPLATE_ACCESS_REQUEST = 'Access request template'
    public static final String APS_MESSAGE_TEMPLATE_TERMINATE_REQUEST = 'Access terminate request template'
    public static final String APS_MESSAGE_TEMPLATE_ACCESS_REQUEST_APPROVED = 'Access request approved template'
    public static final String APS_MESSAGE_TEMPLATE_24H_REVOKE_REQUEST = 'Access revoke request 24 hours template'
    public static final String APS_MESSAGE_TEMPLATE_24H_REVOKE_REQUEST_ESCALATION = 'Access revoke request 24 hours escalation template'
    public static final String APS_MESSAGE_TEMPLATE_24H_REVOKE_REQUEST_NOTIFICATION = 'Access revoke request 24 hours notification template'
    public static final String APS_MESSAGE_TEMPLATE_7D_REVOKE_REQUEST = 'Access revoke request 7 days template'
    public static final String APS_MESSAGE_TEMPLATE_7D_REVOKE_REQUEST_ESCALATION = 'Access revoke request 7 days escalation template'
    public static final String APS_MESSAGE_TEMPLATE_7D_REVOKE_REQUEST_NOTIFICATION = 'Access revoke request 7 days notification template'
    public static final String APS_MESSAGE_TEMPLATE_24H_REVOKE_REQUEST_COMPLETE = '24 Hours revocation complete'
    public static final String APS_MESSAGE_TEMPLATE_7D_REVOKE_REQUEST_COMPLETE = '7 Days revocation complete'
    public static final String APS_MESSAGE_TEMPLATE_GATEKEEPER_REJECTION_NOTIFICATION = 'Access request Gatekeeper rejection notification template'
    public static final String APS_MESSAGE_TEMPLATE_ACCESS_VERIFICATION = 'Access verification template'
    public static final String APS_MESSAGE_TEMPLATE_REVOKE_REQUEST_APPROVAL_GATEKEEPER = 'Access Revoke Request Approval by Gatekeeper'

    public static final String REPORT_FIELD_EMPTY_TOKEN = " - "

    public static final Map<String, String> CARE_PRIORITY_MAIL_HEADERS = ['X-Priority': '1', 'X-MSMail-Priority': 'High', 'Importance': 'High']

    public static final String CERTIFICATION_EXPIRATION_NOTIFICATION_TEMPLATE_WILDCARD = '*'

    public static final String GATEKEEPER_REJECTION_NOTIFICATION_EMAIL_APS_SYSTEM_TASK = "GATEKEEPER_REJECTION_NOTIFICATION_EMAIL_APS_SYSTEM_TASK"
    public static final String GATEKEEPER_REJECTION_NOTIFICATION_EMAIL_APS_SYSTEM_TASK_FOR_CONTRACTOR = "GATEKEEPER_REJECTION_NOTIFICATION_EMAIL_APS_SYSTEM_TASK_FOR_CONTRACTOR"
    public static final String ENTITLEMENT_ROLE_PROVISIONED_NOTIFICATION_EMAIL_CENTRAL_SYSTEM_TASK = "ENTITLEMENT_ROLE_PROVISIONED_NOTIFICATION_EMAIL_CENTRAL_SYSTEM_TASK"
    public static final String ENTITLEMENT_ROLE_PROVISIONED_NOTIFICATION_EMAIL_CENTRAL_SYSTEM_TASK_FOR_CONTRACTOR = "ENTITLEMENT_ROLE_PROVISIONED_NOTIFICATION_EMAIL_CENTRAL_SYSTEM_TASK_FOR_CONTRACTOR"
    public static final String CENTRAL_MESSAGE_TEMPLATE_ENTITLEMENT_ROLE_PROVISIONED_NOTIFICATION = 'Access request Entitlement Role provisioned notification template'

    public static final String FEED_EMPLOYEE_HR_INFO = "Employee HR Info"
    public static final String FEED_CONTRACTOR_HR_INFO = "Contractor HR Info"
    public static final String FEED_EMPLOYEE_COURSE = "Employee Course"
    public static final String FEED_CONTRACTOR_COURSE = "Contractor Course"
    public static final String FEED_EMPLOYEE_PRA = "Employee PRA"
    public static final String FEED_CONTRACTOR_PRA = "Contractor PRA"
    public static final String FEED_ACTIVE_WORKER = "Active Worker"

    public static final String DEFAULT_TAG_FOR_ENTITLEMENT_ROLE = "Misc"

    public static final String ACCESS_TYPE_PHYSICAL_ONLY = "Physical Only"
    public static final String ACCESS_TYPE_CYBER_ONLY = "Cyber Only"
    public static final String ACCESS_TYPE_BOTH = "Physical & Cyber"

    public static final String CANNED_RESPONSE_CENTRAL_ACCESS_REQUEST_JUSTIFICATION = "CANNED_RESPONSE_CENTRAL_ACCESS_REQUEST_JUSTIFICATION"
    public static final String CANNED_RESPONSE_CENTRAL_REVOKE_REQUEST_JUSTIFICATION = "CANNED_RESPONSE_CENTRAL_REVOKE_REQUEST_JUSTIFICATION"
    public static final String CANNED_RESPONSE_CENTRAL_ACCESS_REQUEST_SUPERVISOR_APPROVAL = "CANNED_RESPONSE_CENTRAL_ACCESS_REQUEST_SUPERVISOR_APPROVAL"

    public static final String CANNED_RESPONSE_APS_ACCESS_VERIFICATION_JUSTIFICATION = "CANNED_RESPONSE_APS_ACCESS_VERIFICATION_JUSTIFICATION"
    public static final String CANNED_RESPONSE_APS_ACCESS_REQUEST_GATEKEEPER_JUSTIFICATION = "CANNED_RESPONSE_APS_ACCESS_REQUEST_GATEKEEPER_JUSTIFICATION"
    public static final String CANNED_RESPONSE_APS_ACCESS_REQUEST_PROVISIONER_JUSTIFICATION = "CANNED_RESPONSE_APS_ACCESS_REQUEST_PROVISIONER_JUSTIFICATION"
    public static final String CANNED_RESPONSE_APS_REVOKE_REQUEST_GATEKEEPER_JUSTIFICATION = "CANNED_RESPONSE_APS_REVOKE_REQUEST_GATEKEEPER_JUSTIFICATION"
    public static final String CANNED_RESPONSE_APS_REVOKE_REQUEST_PROVISIONER_JUSTIFICATION = "CANNED_RESPONSE_APS_REVOKE_REQUEST_PROVISIONER_JUSTIFICATION"
    public static final String CANNED_RESPONSE_APS_USER_RESPONSE = "CANNED_RESPONSE_APS_USER_RESPONSE"
    public static final String WORKER_TYPE_EMPLOYEE = 'Employees'
    public static final String WORKER_TYPE_CONTRACTOR = 'Contractors'

    public static final String VIEW_TYPE_GLOBAL = 'Global'
    public static final String VIEW_TYPE_SUPERVISOR = 'Supervisor'

    public static final String WORKFLOW_TYPE_FOR_CREATE_ENTITLEMENT_FROM_FILE_FEED = "CREATE_ENTITLEMENT_FROM_FEED_WORKFLOW"
    public static final String WORKFLOW_TYPE_FOR_UPDATE_ENTITLEMENT_FROM_FILE_FEED = "UPDATE_ENTITLEMENT_FROM_FEED_WORKFLOW"

    public static final String TIM_RAW_SERVLET_CREATE_PERSON_PATH = "getTimResponse/createPerson"
    public static final String TIM_RAW_SERVLET_SUSPEND_PERSON_PATH = "getTimResponse/suspendPerson"
    public static final String TIM_RAW_SERVLET_ADD_ROLES_TO_PERSON_PATH = "getTimResponse/addRolesToPerson"
    public static final String TIM_RAW_SERVLET_REMOVE_ROLES_FROM_PERSON_PATH = "getTimResponse/removeRolesFromPerson"
    public static final String TIM_RAW_SERVLET_GET_ROLES_PATH = "getTimResponse/getRoles"
    public static final String TIM_RAW_SERVLET_GET_ROLES_WITH_SLID_PATH = "getTimResponse/getRolesWithSlid"
    public static final String TIM_RAW_SERVLET_GET_PEOPLE_PATH = "getTimResponse/getPeople"
    public static final String TIM_SERVLET_GET_REQUEST_STATUS_PATH = "getTimResponse/getRequestStatus"

    public static final String DUMMY_BUSINESS_UNIT = "Dummy Business Unit"

    public static final String ACCESS_REQUEST = "ACCESS REQUEST"
    public static final String REVOKE_REQUEST = "REVOKE REQUEST"

    public static final String SHARED_ACCOUNT_ATTRIBUTE = "Shared Account"
    public static final String GENERIC_ACCOUNT_ATTRIBUTE = "Generic Account"
    public static final String LAST_PASSWORD_CHANGE_ATTRIBUTE = "Last Password Change"
}