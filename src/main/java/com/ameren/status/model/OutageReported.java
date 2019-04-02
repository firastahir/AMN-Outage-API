package com.ameren.status.model;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OutageReported {

	private int callId;
	private Timestamp processTimestamp;
	private int reportedOutageId;
	private String transformer;
	private String source;
	private Timestamp createTimestamp;
	private String serviceState;
	private boolean processed;
	private String billAccount;
	private String processedFlag;

	public OutageReported() {

	}

	public OutageReported(
			@JsonProperty("callId")int callId,
			@JsonProperty("processTimestamp")Timestamp processTimestamp,
			@JsonProperty("reportedOutageId")int reportedOutageId,
			@JsonProperty("transformer")String transformer,
			@JsonProperty("source")String source,
			@JsonProperty("createTimestamp")Timestamp createTimestamp,
			@JsonProperty("serviceState")String serviceState,
			@JsonProperty("processed")boolean processed,
			@JsonProperty("billAccount")String billAccount,
			@JsonProperty("processedFlag")String processedFlag)
    {
		this.callId = callId;
		this.processTimestamp = processTimestamp;
		this.reportedOutageId = reportedOutageId;
		this.transformer = transformer;
		this.source = source;
		this.createTimestamp = createTimestamp;
		this.serviceState = serviceState;
		this.processed = processed;
		this.billAccount = billAccount;
		this.processedFlag = processedFlag;
    }

	public int getCallId() {
		return callId;
	}

	public void setCallId(int callId) {
		this.callId = callId;
	}

	public Timestamp getProcessTimestamp() {
		return processTimestamp;
	}

	public void setProcessTimestamp(Timestamp processTimestamp) {
		this.processTimestamp = processTimestamp;
	}

	public int getReportedOutageId() {
		return reportedOutageId;
	}

	public void setReportedOutageId(int reportedOutageId) {
		this.reportedOutageId = reportedOutageId;
	}

	public String getTransformer() {
		return transformer;
	}

	public void setTransformer(String transformer) {
		this.transformer = transformer;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Timestamp getCreateTimestamp() {
		return createTimestamp;
	}

	public void setCreateTimestamp(Timestamp createTimestamp) {
		this.createTimestamp = createTimestamp;
	}

	public String getServiceState() {
		return serviceState;
	}

	public void setServiceState(String serviceState) {
		this.serviceState = serviceState;
	}

	public boolean getProcessed() {
		return processed;
	}

	public void setProcessed(boolean processed) {
		this.processed = processed;
	}

	public String getBillAccount() {
		return billAccount;
	}

	public void setBillAccount(String billAccount) {
		this.billAccount = billAccount;
	}

	public String getProcessedFlag() {
		return processedFlag;
	}

	public void setProcessedFlag(String processedFlag) {
		this.processedFlag = processedFlag;
	}

}
