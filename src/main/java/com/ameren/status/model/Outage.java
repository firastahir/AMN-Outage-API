package com.ameren.status.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Outage {
	
	private boolean isPowerOn;
	private String billAccountNumber;
	private String premiseNumber;
	private int companyCode;
	private String state;
	private String postalCode;
	private String houseNumber;
	private String phoneNumber;
	private String transformerId;
	private String status;
	private String cause;
	private String startDate;
	private String esrt;
	private int affectedCustomers;
	private String restoredDate;
	private boolean processed;
	private String lastUpdateDate;
	private String originalEstimatedResorationDateTime;
	private String callBackNumber;
	private boolean reportAlert;
	
	public Outage() {
		
	}

	public Outage(@JsonProperty("isPowerOn")boolean isPowerOn,
			@JsonProperty("billAccountNumber")String billAccountNumber,
			@JsonProperty("premiseNumber")String premiseNumber,
			@JsonProperty("companyCode")int companyCode,
			@JsonProperty("state")String state,
			@JsonProperty("postalCode")String postalCode,
			@JsonProperty("houseNumber")String houseNumber,
			@JsonProperty("phoneNumber")String phoneNumber,
			@JsonProperty("transformerID")String transformerId,
			@JsonProperty("outageStatusDescription")String status,
			@JsonProperty("outageCauseDescription")String cause,
			@JsonProperty("outageDateTime")String startDate,
			@JsonProperty("estimatedRestorationDateTime")String esrt,
			@JsonProperty("affectedNumberOfCustomers")int affectedCustomers,
			@JsonProperty("restoredDateTime")String restoredDate,
			@JsonProperty("reportedNotProcessed")boolean processed,
			@JsonProperty("lastUpdatedDateTime")String lastUpdateDate)
	{
		this.isPowerOn = isPowerOn;
		this.billAccountNumber = billAccountNumber;
		this.premiseNumber = premiseNumber;
		this.companyCode = companyCode;
		this.state = state;
		this.postalCode = postalCode;
		this.houseNumber = houseNumber;
		this.phoneNumber = phoneNumber;
		this.transformerId = transformerId;
		this.status = status;
		this.cause = cause;
		this.startDate = startDate;
		this.esrt = esrt;
		this.affectedCustomers = affectedCustomers;
		this.restoredDate = restoredDate;
		this.processed = processed;
		this.lastUpdateDate = lastUpdateDate;	
	}

	public boolean isPowerOn() {
		return isPowerOn;
	}

	public void setPowerOn(boolean isPowerOn) {
		this.isPowerOn = isPowerOn;
	}

	public String getBillAccountNumber() {
		return billAccountNumber;
	}

	public void setBillAccountNumber(String billAccountNumber) {
		this.billAccountNumber = billAccountNumber;
	}

	public String getPremiseNumber() {
		return premiseNumber;
	}

	public void setPremiseNumber(String premiseNumber) {
		this.premiseNumber = premiseNumber;
	}

	public int getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(int companyCode) {
		this.companyCode = companyCode;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getHouseNumber() {
		return houseNumber;
	}

	public void setHouseNumber(String houseNumber) {
		this.houseNumber = houseNumber;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getTransformerId() {
		return transformerId;
	}

	public void setTransformerId(String transformerId) {
		this.transformerId = transformerId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCause() {
		return cause;
	}

	public void setCause(String cause) {
		this.cause = cause;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEsrt() {
		return esrt;
	}

	public void setEsrt(String esrt) {
		this.esrt = esrt;
	}

	public int getAffectedCustomers() {
		return affectedCustomers;
	}

	public void setAffectedCustomers(int affectedCustomers) {
		this.affectedCustomers = affectedCustomers;
	}

	public String getRestoredDate() {
		return restoredDate;
	}

	public void setRestoredDate(String restoredDate) {
		this.restoredDate = restoredDate;
	}

	public boolean isProcessed() {
		return processed;
	}

	public void setProcessed(boolean processed) {
		this.processed = processed;
	}

	public String getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(String lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public String getOriginalEstimatedResorationDateTime() {
		return originalEstimatedResorationDateTime;
	}

	public void setOriginalEstimatedResorationDateTime(String originalEstimatedResorationDateTime) {
		this.originalEstimatedResorationDateTime = originalEstimatedResorationDateTime;
	}

	public String getCallBackNumber() {
		return callBackNumber;
	}

	public void setCallBackNumber(String callBackNumber) {
		this.callBackNumber = callBackNumber;
	}

	public boolean isReportAlert() {
		return reportAlert;
	}

	public void setReportAlert(boolean reportAlert) {
		this.reportAlert = reportAlert;
	}
}
