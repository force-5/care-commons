package com.force5solutions.care.feed

enum FeedReportMessageType {
	INFO('Info'),
	WARNING('Warning'),
	EXCEPTION("Exception"), // exception is business exception
	ERROR("Error") // error is an un-handled case, such as NPE,


	private final String name;

	FeedReportMessageType(String name) {
		this.name = name;
	}

	public String toString() {
		return name;
	}


}