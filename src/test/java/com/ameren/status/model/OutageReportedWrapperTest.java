package com.ameren.status.model;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class OutageReportedWrapperTest {

	private final String account = "2133992787";
	
	@Test
	public void outageReportedWrapper_validateHasReportedTrue() {
		//mock reported calls
		OutageReportedCalls outageReportedCalls = new OutageReportedCalls();
		OutageReported outageReported = new OutageReported();
		outageReported.setCallId(123);
		outageReported.setBillAccount(account);
		outageReported.setTransformer("12345678900");
		outageReported.setSource("CallEntry");
		outageReported.setReportedOutageId(123);
		outageReported.setProcessed(false);
		outageReported.setProcessedFlag("0");	
		OutageReportedWrapper outageReportedWrapper = new OutageReportedWrapper();
		outageReportedWrapper.setData(outageReportedCalls);
		outageReportedWrapper.setHasError(false);
		outageReportedWrapper.setMessage("");
		outageReportedWrapper.setStatusCode(200);
		
		assertNotNull(outageReportedWrapper.getData());
		assertTrue(outageReportedWrapper.hasReported());
	}
	
	@Test
	public void outageReportedWrapper_validateHasReportedFalse() {
		//mock reported calls
		OutageReportedWrapper outageReportedWrapper = new OutageReportedWrapper();
		outageReportedWrapper.setHasError(false);
		outageReportedWrapper.setMessage("");
		outageReportedWrapper.setStatusCode(200);

		assertNull(outageReportedWrapper.getData());
		assertFalse(outageReportedWrapper.hasReported());
	}
	
}
