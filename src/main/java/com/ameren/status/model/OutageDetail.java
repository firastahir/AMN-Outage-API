package com.ameren.status.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OutageDetail {

	private Outage outage;
	
	public OutageDetail() {
		
	}
	
	public OutageDetail(@JsonProperty("outage")Outage outage)
	{
		this.outage = outage;
	}

	public Outage getOutage() {
		return outage;
	}

	public void setOutage(Outage outage) {
		this.outage = outage;
	}
	
	
	
}
