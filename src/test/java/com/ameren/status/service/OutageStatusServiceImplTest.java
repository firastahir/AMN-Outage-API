package com.ameren.status.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.when;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.net.URI;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.ameren.status.config.OutageStatusConfig;
import com.ameren.status.model.OutageDetailWrapper;
import com.ameren.status.model.OutageReportedCalls;
import com.ameren.status.model.OutageReportedWrapper;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(MockitoJUnitRunner.class)
public class OutageStatusServiceImplTest {

	@InjectMocks
	private OutageStatusServiceImpl outageStatusService;
	
	@Mock
	private RestTemplate restTemplate;
	
	@Test
	public void retrieveReported_verifyDataElementIsNotNullWhenIsReported() {
		
		String url = OutageStatusConfig.REPORTED_OUTAGES_URL = "reportUrl";
		String account = "2133992787";
		
		// set up mocked Outage Reported Service Response
		OutageReportedCalls outageReportedCalls = new OutageReportedCalls();
		OutageReportedWrapper outageReportedWrapper = new OutageReportedWrapper();
		outageReportedWrapper.setData(outageReportedCalls);
		outageReportedWrapper.setHasError(false);
		outageReportedWrapper.setMessage("");
		outageReportedWrapper.setStatusCode(200);
		
		//set up mock for restTemplate
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    HttpEntity<Object> httpRequest;
	    httpRequest = new HttpEntity<>(headers);
		UriComponentsBuilder builder;
		builder = UriComponentsBuilder.fromUriString(String.format(url));
	    URI uriBuilder = builder.buildAndExpand().toUri();
		
	    // mock restTemplate call
		when(restTemplate.exchange(uriBuilder, HttpMethod.GET, httpRequest, OutageReportedWrapper.class)).thenReturn(new ResponseEntity<OutageReportedWrapper>(outageReportedWrapper, HttpStatus.OK));

		// call Outage Reported Service
		OutageReportedWrapper response = outageStatusService.retrieveOutageReported(account);

		// assert 
		assertNotNull(response.getData());
	}
	
	@Test
	public void retrieveReported_verifyDataElementIsNullWhenNotReported() {
		
		String url = OutageStatusConfig.REPORTED_OUTAGES_URL = "reportUrl";
		String account = "2133992787";
		
		// set up mocked Outage Reported Service Response
		OutageReportedWrapper outageReportedWrapper = new OutageReportedWrapper();
		outageReportedWrapper.setHasError(false);
		outageReportedWrapper.setMessage("No Data");
		outageReportedWrapper.setStatusCode(200);
		
		//set up mock for restTemplate
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    HttpEntity<Object> httpRequest;
	    httpRequest = new HttpEntity<>(headers);
		UriComponentsBuilder builder;
		builder = UriComponentsBuilder.fromUriString(String.format(url));
	    URI uriBuilder = builder.buildAndExpand().toUri();
		
	    // mock restTemplate call
		when(restTemplate.exchange(uriBuilder, HttpMethod.GET, httpRequest, OutageReportedWrapper.class)).thenReturn(new ResponseEntity<OutageReportedWrapper>(outageReportedWrapper, HttpStatus.OK));

		// call Outage Reported Service
		OutageReportedWrapper response = outageStatusService.retrieveOutageReported(account);

		// assert 
		assertNull(response.getData());
	}
	
	@Test
	public void retrieveReported_whenRquestToSeeIfCustomerAlreadyReported_sendTheHttpStatusCodeFromTheReportedOutagesAPI() {
		
		String url = OutageStatusConfig.REPORTED_OUTAGES_URL = "reportUrl";
		String account = "2133992787";
		
		//set up mock for restTemplate
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    HttpEntity<Object> httpRequest;
	    httpRequest = new HttpEntity<>(headers);
		UriComponentsBuilder builder;
		builder = UriComponentsBuilder.fromUriString(String.format(url));
	    URI uriBuilder = builder.buildAndExpand().toUri();
		
	    // mock restTemplate call
		when(restTemplate.exchange(uriBuilder, HttpMethod.GET, httpRequest, OutageReportedWrapper.class)).thenThrow(new HttpServerErrorException(HttpStatus.SERVICE_UNAVAILABLE));

		// call Outage Reported Service
		OutageReportedWrapper response = outageStatusService.retrieveOutageReported(account);

		// assert 
		assertEquals(HttpStatus.SERVICE_UNAVAILABLE.value(),response.getStatusCode());
	}
	
	@Test
	public void retrieveOutageDetail_mapDataProperly() throws JsonParseException, JsonMappingException, IOException {
		
		String url = OutageStatusConfig.OUTAGE_STATUS_URL = "statusUrl";
		String account = "2133992787";
		
		// set up mocked Outage Reported Service Response		
		String jsonStatusCodeProperty = "{\"statusCode\": \"200\"";
		String jsonMessageProperty = "\"message\": \"Message returned from CSS Service - [Success] \"";
		String jsonHasErrorProperty = "\"hasError\": false";
		String jsonDataProperty =     "\"data\": {"
		     + "\"outage\": {"
	            +"\"isPowerOn\": false,"
	            +"\"billAccountNumber\": \"6623201117\","
	            +"\"premiseNumber\": \"662320101\","
	            +"\"companyCode\": 17,"
	            +"\"state\": \"MO\","
	            +"\"postalCode\": \"63021\","
	            +"\"houseNumber\": \"320\","
	            +"\"phoneNumber\": \"3146083168\","
	            +"\"transformerID\": \"02518411003\","
	            +"\"outageStatusDescription\": \"Order has been received\","
	            +"\"outageCauseDescription\": \"Cause not yet determined\","
	            +"\"outageDateTime\": \"2018-02-23T12:38:00\","
	            +"\"estimatedRestorationDateTime\": \"0001-01-01T00:00:00\","
	            +"\"affectedNumberOfCustomers\": 1,"
	            +"\"restoredDateTime\": \"0001-01-01T00:00:00\","
	            +"\"reportedNotProcessed\": false,"
	            +"\"lastUpdatedDateTime\": \"2018-04-11T15:00:13.414\""
	        + "},"
	        +"\"message\": {"
	        + "   \"code\": \"Success\"}}}";

		String jsonMessage = jsonStatusCodeProperty + ", " + jsonMessageProperty + ", " + jsonHasErrorProperty + ", " + jsonDataProperty;
		ObjectMapper objectMapper = new ObjectMapper();
		
		OutageDetailWrapper outageDetailWrapper = objectMapper.readValue(jsonMessage, OutageDetailWrapper.class);

		//set up mock for restTemplate
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    HttpEntity<Object> httpRequest;
	    httpRequest = new HttpEntity<>(headers);
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(String.format(url, "6623201117"));
	    URI uriBuilder = builder.buildAndExpand().toUri();
		
	    // mock restTemplate call
		when(restTemplate.exchange(uriBuilder, HttpMethod.GET, httpRequest, OutageDetailWrapper.class)).thenReturn(new ResponseEntity<OutageDetailWrapper>(outageDetailWrapper, HttpStatus.OK));

		// call Outage Reported Service
		OutageDetailWrapper response = outageStatusService.retrieveOutageDetail(account);

		// assert
		assertSame(response, outageDetailWrapper);
	}
	
	@Test(expected = HttpStatusCodeException.class)
	public void retrieveOutageDetail_ThrowsException() {
		//arrange
		String url = OutageStatusConfig.OUTAGE_STATUS_URL = "reportUrl";
		String account = "2133992787";
		
		//set up mock for restTemplate
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    HttpEntity<Object> httpRequest;
	    httpRequest = new HttpEntity<>(headers);
		UriComponentsBuilder builder;
		builder = UriComponentsBuilder.fromUriString(String.format(url, account));
	    URI uriBuilder = builder.buildAndExpand().toUri();
	    // mock call to Outage Status API returning Service Unavailable
		when(restTemplate.exchange(uriBuilder, HttpMethod.GET, httpRequest, OutageDetailWrapper.class)).thenThrow(new HttpServerErrorException(HttpStatus.SERVICE_UNAVAILABLE));

		//act
		outageStatusService.retrieveOutageDetail(account);
		
		// assert 
		// No Assertions needed as @Test is expecting the correct Exception
	}	
}
