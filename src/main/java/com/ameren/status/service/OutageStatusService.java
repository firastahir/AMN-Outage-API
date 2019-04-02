package com.ameren.status.service;

import com.ameren.status.model.OutageDetailWrapper;
import com.ameren.status.model.OutageReportedWrapper;

public interface OutageStatusService {

	
	public OutageReportedWrapper retrieveOutageReported(String account);
		
	public OutageDetailWrapper retrieveOutageDetail(String account);
}
