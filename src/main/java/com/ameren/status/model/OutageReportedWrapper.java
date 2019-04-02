package com.ameren.status.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OutageReportedWrapper {

	private int statusCode;
	
	private String message;
	
	private boolean hasError;

	private OutageReportedCalls data;
	
	public OutageReportedWrapper() {
	
	}
	
	public OutageReportedWrapper(
			@JsonProperty("StatusCode")int statusCode, 
			@JsonProperty("Message")String message, 
			@JsonProperty("HasError")boolean hasError, 
			@JsonProperty("Data")OutageReportedCalls data) 
	{
		
		this.statusCode = statusCode;
		this.message = message;
		this.hasError = hasError;
		this.data = data;
	}
	
	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isHasError() {
		return hasError;
	}

	public void setHasError(boolean hasError) {
		this.hasError = hasError;
	}

	public OutageReportedCalls getData() {
		return data;
	}

	public void setData(OutageReportedCalls data) {
		this.data = data;
	}

	public boolean hasReported(){
		return this.data != null ? true : false;
	}
}
