package com.ameren.status.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OutageReportedCalls {

	private List<OutageReported> outageCalls;
	
	public OutageReportedCalls() {
		
	}
	
	public OutageReportedCalls(@JsonProperty("calls")List<OutageReported> outageCalls) 
	{
		this.outageCalls = outageCalls;
	}
	
	public List<OutageReported> getOutageCalls() {
		return outageCalls;
	}

	public void setOutageCalls(List<OutageReported> outageCalls) {
		this.outageCalls = outageCalls;
	}
}
