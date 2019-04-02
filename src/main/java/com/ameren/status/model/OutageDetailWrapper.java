package com.ameren.status.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OutageDetailWrapper {
	private int statusCode;

	private String message;
	
	private boolean hasError;

	private OutageDetail data;
	
	public OutageDetailWrapper() {
	
	}
	
	public OutageDetailWrapper(
			@JsonProperty("StatusCode")int statusCode, 
			@JsonProperty("Message")String message, 
			@JsonProperty("HasError")boolean hasError, 
			@JsonProperty("Data")OutageDetail data) 
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

	public OutageDetail getData() {
		return data;
	}

	public void setData(OutageDetail data) {
		this.data = data;
	}
	
	public boolean isKnownOutage() {
		return !(this.getData().getOutage().isPowerOn() && this.getData().getOutage().getAffectedCustomers() == 0);
	}
}
