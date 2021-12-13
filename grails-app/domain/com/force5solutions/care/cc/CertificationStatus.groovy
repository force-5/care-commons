package com.force5solutions.care.cc

public class CertificationStatus {
    Date dateCreated
    Date lastUpdated
    
    String status
    Date date = new Date()
    String provider
	
	static constraints = {
		status (nullable:true)
		provider(nullable:true)
	}
}