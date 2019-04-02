package com.ameren.status.api;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ameren.status.config.OutageStatusConfig;
import com.ameren.status.model.Outage;
import com.ameren.status.model.OutageDetail;
import com.ameren.status.model.OutageDetailWrapper;
import com.ameren.status.model.OutageReported;
import com.ameren.status.model.OutageReportedCalls;
import com.ameren.status.model.OutageReportedWrapper;
import com.ameren.status.model.OutageStatus;
import com.ameren.status.service.OutageStatusService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@RunWith(MockitoJUnitRunner.class)
public class OutageStatusControllerTest {

	@InjectMocks
	private OutageStatusController accountsApiController;
	
	@Mock
	private OutageStatusService outageStatusService;

	private ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
	
	@Test
	public void statusRequest_UnknownAndReportedOutage() {

		OutageStatusConfig.REPORTED_OUTAGES_URL = "reportUrl";
		String account = "2133992787";
		
		//mock outage details
		OutageDetailWrapper outageDetailWrapper = new OutageDetailWrapper();
		OutageDetail outageDetail = new OutageDetail();
		Outage outage = new Outage();
		outage.setPowerOn(true);
		outage.setBillAccountNumber(account);
		outage.setAffectedCustomers(0);
		outageDetail.setOutage(outage);
		outageDetailWrapper.setData(outageDetail);
		outageDetailWrapper.setHasError(false);
		outageDetailWrapper.setMessage("");
		outageDetailWrapper.setStatusCode(200);
		
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
		
		// mock service calls
		when(outageStatusService.retrieveOutageDetail(account)).thenReturn(outageDetailWrapper);
		when(outageStatusService.retrieveOutageReported(account)).thenReturn(outageReportedWrapper);

		// call statusRequest
		ResponseEntity<OutageStatus> response = accountsApiController.statusRequest(account);
		
		// assert
		verify(outageStatusService, times(1)).retrieveOutageDetail(account);
		verify(outageStatusService, times(1)).retrieveOutageReported(account);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(account, response.getBody().getAccount());
		assertEquals(false, response.getBody().isKnownOutage());
		assertEquals(true, response.getBody().isReported());
	}

	@Test
	public void statusRequest_UnknownAndNotReportedOutage() {

		OutageStatusConfig.REPORTED_OUTAGES_URL = "reportUrl";
		String account = "0382238097";
		
		//mock outage details
		OutageDetailWrapper outageDetailWrapper = new OutageDetailWrapper();
		OutageDetail outageDetail = new OutageDetail();
		Outage outage = new Outage();
		outage.setPowerOn(true);
		outage.setBillAccountNumber(account);
		outage.setAffectedCustomers(0);
		outageDetail.setOutage(outage);
		outageDetailWrapper.setData(outageDetail);
		outageDetailWrapper.setHasError(false);
		outageDetailWrapper.setMessage("");
		outageDetailWrapper.setStatusCode(200);
		
		// set up mocked Outage Reported Service Response
		OutageReportedWrapper outageReportedWrapper = new OutageReportedWrapper();
		outageReportedWrapper.setHasError(false);
		outageReportedWrapper.setMessage("No Data");
		outageReportedWrapper.setStatusCode(200);
		
		// mock service calls
		when(outageStatusService.retrieveOutageDetail(account)).thenReturn(outageDetailWrapper);
		when(outageStatusService.retrieveOutageReported(account)).thenReturn((outageReportedWrapper));
		
		// call statusRequest
		ResponseEntity<OutageStatus> response = accountsApiController.statusRequest(account);
		
		// assert
		verify(outageStatusService, times(1)).retrieveOutageDetail(account);
		verify(outageStatusService, times(1)).retrieveOutageReported(account);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(account, response.getBody().getAccount());
		assertEquals(false, response.getBody().isKnownOutage());
		assertEquals(false, response.getBody().isReported());
	}
	
	@Test
	public void statusRequest_KnownAndReportedOutage() {
		OutageStatusConfig.REPORTED_OUTAGES_URL = "reportUrl";
		String account = "0382238097";
		
		//mock outage details
		OutageDetailWrapper outageDetailWrapper = new OutageDetailWrapper();
		OutageDetail outageDetail = new OutageDetail();
		Outage outage = new Outage();
		outage.setPowerOn(false);
		outage.setBillAccountNumber(account);
		outage.setState("MO");
		outage.setPostalCode("63049");
		outage.setHouseNumber("12345");
		outage.setEsrt("2018-02-13T23:15:12.049");
		outage.setAffectedCustomers(10);
		outage.setCause("crew assigned");
		outage.setStatus("UK");
		outage.setStartDate("2018-02-13T23:15:12");
		outage.setRestoredDate("2018-02-13T23:15:12.049");
		outage.setLastUpdateDate("2018-02-13T23:15:12.049");
		outageDetail.setOutage(outage);
		outageDetailWrapper.setData(outageDetail);
		outageDetailWrapper.setHasError(false);
		outageDetailWrapper.setMessage("");
		outageDetailWrapper.setStatusCode(200);
		
		// set up mocked Outage Reported Service Response
		OutageReportedCalls outageReportedCalls = new OutageReportedCalls();
		OutageReported outageReported = new OutageReported();
		outageReported.setBillAccount(account);
		OutageReportedWrapper outageReportedWrapper = new OutageReportedWrapper();
		outageReportedWrapper.setData(outageReportedCalls);
		outageReportedWrapper.setHasError(false);
		outageReportedWrapper.setMessage("");
		outageReportedWrapper.setStatusCode(200);

		// mock service calls
		when(outageStatusService.retrieveOutageDetail(account)).thenReturn(outageDetailWrapper);
		when(outageStatusService.retrieveOutageReported(account)).thenReturn((outageReportedWrapper));
		
		// call statusRequest
		ResponseEntity<OutageStatus> response = accountsApiController.statusRequest(account);
		
		//assert - payload to kubra
		Integer affectedCustomersInteger = outage.getAffectedCustomers(); // Auto Boxing
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(true, response.getBody().isKnownOutage());
		assertEquals(true, response.getBody().isReported());
		assertEquals(account, response.getBody().getAccount());
		assertEquals(outage.getState(), response.getBody().getState());
		assertEquals(outage.getPostalCode(), response.getBody().getPostalCode());
		assertEquals(outage.getHouseNumber(), response.getBody().getHouseNumber());
		assertEquals(affectedCustomersInteger, response.getBody().getCustomersImpacted());
		assertEquals(outage.getCause(), response.getBody().getCause());
		assertEquals(outage.getStatus(), response.getBody().getOutageStatus());

		assertEquals("2018-02-13T23:15:12.049-06:00", response.getBody().getEsrt().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
		assertEquals("2018-02-13T23:15:12-06:00", response.getBody().getOutageDateTime().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
		assertEquals("2018-02-13T23:15:12.049-06:00", response.getBody().getRestoredDateTime().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
		assertEquals("2018-02-13T23:15:12.049-06:00", response.getBody().getLastUpdatedDateTime().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
	}
	
	@Test
	public void dateTimeFieldsAreZoneAwareWithRespectToDaylightSavings() {
		OutageStatusConfig.REPORTED_OUTAGES_URL = "reportUrl";
		String account = "0382238097";
		
		//mock outage details
		OutageDetailWrapper outageDetailWrapper = new OutageDetailWrapper();
		OutageDetail outageDetail = new OutageDetail();
		Outage outage = new Outage();
		outage.setPowerOn(false);
		outage.setBillAccountNumber(account);
		outage.setState("MO");
		outage.setPostalCode("63049");
		outage.setHouseNumber("12345");
		outage.setEsrt("2018-02-13T23:15:12.049");
		outage.setAffectedCustomers(10);
		outage.setCause("crew assigned");
		outage.setStatus("UK");
		outage.setStartDate("2018-02-13T23:15:12");
		outage.setRestoredDate("2018-02-13T23:15:12.049");
		outage.setLastUpdateDate("2018-02-13T23:15:12.049");
		outageDetail.setOutage(outage);
		outageDetailWrapper.setData(outageDetail);
		outageDetailWrapper.setHasError(false);
		outageDetailWrapper.setMessage("");
		outageDetailWrapper.setStatusCode(200);
		
		// set up mocked Outage Reported Service Response
		OutageReportedCalls outageReportedCalls = new OutageReportedCalls();
		OutageReported outageReported = new OutageReported();
		outageReported.setBillAccount(account);
		OutageReportedWrapper outageReportedWrapper = new OutageReportedWrapper();
		outageReportedWrapper.setData(outageReportedCalls);
		outageReportedWrapper.setHasError(false);
		outageReportedWrapper.setMessage("");
		outageReportedWrapper.setStatusCode(200);

		// mock service calls
		when(outageStatusService.retrieveOutageDetail(account)).thenReturn(outageDetailWrapper);
		when(outageStatusService.retrieveOutageReported(account)).thenReturn((outageReportedWrapper));
		
		// call statusRequest
		ResponseEntity<OutageStatus> response = accountsApiController.statusRequest(account);
		
		//assert - payload to kubra
//		boolean daylightSavings = ZonedDateTime.now().getZone().getRules().isDaylightSavings(Instant.now());
		//		if(daylightSavings) {
//			timeOffset = "-05:00";
//		}
		assertEquals("2018-02-13T23:15:12-06:00", response.getBody().getOutageDateTime().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
		assertEquals("2018-02-13T23:15:12.049-06:00", response.getBody().getEsrt().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
		assertEquals("2018-02-13T23:15:12.049-06:00", response.getBody().getRestoredDateTime().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
		assertEquals("2018-02-13T23:15:12.049-06:00", response.getBody().getLastUpdatedDateTime().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
		
	}
		
	@Test
	public void statusRequest_KnownAndNotReportedOutage() {
		OutageStatusConfig.REPORTED_OUTAGES_URL = "reportUrl";
		String account = "0382238097";
		
		//mock outage details
		OutageDetailWrapper outageDetailWrapper = new OutageDetailWrapper();
		OutageDetail outageDetail = new OutageDetail();
		Outage outage = new Outage();
		outage.setPowerOn(false);
		outage.setBillAccountNumber(account);
		outage.setState("MO");
		outage.setPostalCode("63049");
		outage.setHouseNumber("12345");
		outage.setEsrt("2018-02-13T23:15:12.049Z");
		outage.setAffectedCustomers(10);
		outage.setCause("crew assigned");
		outage.setStatus("UK");
		outage.setStartDate("2016-01-11T23:15:12.049Z");
		outage.setRestoredDate("2018-02-13T23:15:12.049Z");
		outage.setLastUpdateDate("2018-02-13T23:15:12.049Z");
		outageDetail.setOutage(outage);
		outageDetailWrapper.setData(outageDetail);
		outageDetailWrapper.setHasError(false);
		outageDetailWrapper.setMessage("");
		outageDetailWrapper.setStatusCode(200);
		
		// set up mocked Outage Reported Service Response
		OutageReportedWrapper outageReportedWrapper = new OutageReportedWrapper();
		outageReportedWrapper.setHasError(false);
		outageReportedWrapper.setMessage("No Data");
		outageReportedWrapper.setStatusCode(200);

		// mock service calls
		when(outageStatusService.retrieveOutageDetail(account)).thenReturn(outageDetailWrapper);
		when(outageStatusService.retrieveOutageReported(account)).thenReturn((outageReportedWrapper));
		
		// call statusRequest
		ResponseEntity<OutageStatus> response = accountsApiController.statusRequest(account);
		
		//assert - payload to kubra
		Integer affectedCustomersInteger = outage.getAffectedCustomers(); // Auto Boxing
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(true, response.getBody().isKnownOutage());
		assertEquals(false, response.getBody().isReported());
		assertEquals(account, response.getBody().getAccount());
		assertEquals(outage.getState(), response.getBody().getState());
		assertEquals(outage.getPostalCode(), response.getBody().getPostalCode());
		assertEquals(outage.getHouseNumber(), response.getBody().getHouseNumber());
		assertEquals(affectedCustomersInteger, response.getBody().getCustomersImpacted());
		assertEquals(outage.getCause(), response.getBody().getCause());
		assertEquals(outage.getStatus(), response.getBody().getOutageStatus());
		assertEquals("2018-02-13T23:15:12.049-06:00", response.getBody().getEsrt().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
		assertEquals("2016-01-11T23:15:12.049-06:00", response.getBody().getOutageDateTime().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
		assertEquals("2018-02-13T23:15:12.049-06:00", response.getBody().getRestoredDateTime().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
		assertEquals("2018-02-13T23:15:12.049-06:00", response.getBody().getLastUpdatedDateTime().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
	}
	
	@Test
	public void statusRequest_outageServiceDetailEncountersAnError() throws Exception {
		//arrange
		OutageStatusConfig.REPORTED_OUTAGES_URL = "reportUrl";
		OutageStatusConfig.GENERIC_ERROR_MESSAGE = "Unable to process request.";
		String account = "0382238097";
		
		OutageDetailWrapper odw = mapper.readValue(this.getClass().getResourceAsStream("/outage-status-response-200-no-account-found.json"), OutageDetailWrapper.class);

		// mock service calls
		when(outageStatusService.retrieveOutageDetail(account)).thenReturn(odw);
		
		//act
		ResponseEntity<OutageStatus> response = accountsApiController.statusRequest(account);
		
		// assert
		assertEquals(HttpStatus.OK, response.getStatusCode());
//		assertEquals("Unable to process request.", response.getBody().getMessage());
		assertEquals("Message returned from CSS REST API (SUPP) is Informational The account number submitted for this request is not available. Please contact us if you have a question.", response.getBody().getMessage());
		
		// this verification exists so that we assure that we do not call to get if the
		//customer reported when the call to get outage details fails
		verify(outageStatusService, never()).retrieveOutageReported(account);
	}
	
	@Test
	public void statusRequest_outageServiceReportedEncountersAnError() {

		OutageStatusConfig.REPORTED_OUTAGES_URL = "reportUrl";
		OutageStatusConfig.GENERIC_ERROR_MESSAGE = "Unable to process request.";
		String account = "0382238097";
		
		//mock outage details
		OutageDetailWrapper outageDetailWrapper = new OutageDetailWrapper();
		OutageDetail outageDetail = new OutageDetail();
		Outage outage = new Outage();
		outage.setPowerOn(true);
		outage.setBillAccountNumber(account);
		outage.setState("MO");
		outage.setPostalCode("63049");
		outage.setHouseNumber("12345");
		outage.setAffectedCustomers(0);
		outageDetail.setOutage(outage);
		outageDetailWrapper.setData(outageDetail);
		outageDetailWrapper.setHasError(false);
		outageDetailWrapper.setMessage("");
		outageDetailWrapper.setStatusCode(200);
		
		// set up mocked Outage Reported Service Response
		OutageReportedWrapper outageReportedWrapper = new OutageReportedWrapper();
		outageReportedWrapper.setHasError(false);
		outageReportedWrapper.setMessage("No Data");
		outageReportedWrapper.setStatusCode(400);
		
		// mock service calls
		when(outageStatusService.retrieveOutageDetail(account)).thenReturn(outageDetailWrapper);
		when(outageStatusService.retrieveOutageReported(account)).thenReturn((outageReportedWrapper));
		
		// call statusRequest
		ResponseEntity<OutageStatus> response = accountsApiController.statusRequest(account);
		
		// assert
		//in the case of the call failing where we check if the customer reported the outage
		//it was decided to default to reported = false
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(false, response.getBody().isKnownOutage());
		assertEquals(false, response.getBody().isReported());
	}	
}
