package com.ameren.status.api;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.ameren.status.config.OutageStatusConfig;
import com.ameren.status.model.Outage;
import com.ameren.status.model.OutageDetailWrapper;
import com.ameren.status.model.OutageReportedWrapper;
import com.ameren.status.model.OutageStatus;
import com.ameren.status.service.OutageStatusService;

@Controller
public class OutageStatusController implements OutageStatusApi {

	private static final ZoneId ZONE_ID = ZoneId.of("America/Chicago");

	private static final Logger logger = LoggerFactory.getLogger(OutageStatusController.class);

	private OutageStatusService outageStatusService;
	
	private RestTemplate restTemplate;
	
	@Autowired
	public OutageStatusController(RestTemplate restTemplate, OutageStatusService outageStatusService){
		this.restTemplate = restTemplate;
		this.outageStatusService = outageStatusService;
	}

	@Override
	@RequestMapping(value = "/accounts/{account}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<OutageStatus> statusRequest(@PathVariable String account) {
		logger.info("Outage Status Started for Account Number {}", account);
		
		OutageDetailWrapper outageDetailWrapper = outageStatusService.retrieveOutageDetail(account);
		if(outageDetailWrapper.getData() == null)
		{
			return ResponseEntity.ok(transformEmptyDataToOutageStatus(outageDetailWrapper.getMessage()));
		}
		
		OutageReportedWrapper outageReportedWrapper = outageStatusService.retrieveOutageReported(account);
		
		OutageStatus outageStatus = transformDataToOutageStatus(account, outageReportedWrapper, outageDetailWrapper);
		logger.info("Payload Returned {}", outageStatus.toString());
		logger.info("Outage Status Completed");
		
		return ResponseEntity.status(HttpStatus.OK).body(outageStatus);
	}

	private OutageStatus transformEmptyDataToOutageStatus(String message) {
		OutageStatus outageStatus = new OutageStatus();
		outageStatus.setMessage(message);
		return outageStatus;
	}

	private OutageStatus transformDataToOutageStatus(String account, OutageReportedWrapper outageReportedWrapper, OutageDetailWrapper outageDetailWrapper) {
		OutageStatus outageStatus = new OutageStatus();
		
		outageStatus.setKnownOutage(outageDetailWrapper.isKnownOutage());
		
		outageStatus.setAccount(account);
		outageStatus.setReported(outageReportedWrapper.hasReported());
		
		Outage stat = outageDetailWrapper.getData().getOutage();
		outageStatus.setState(stat.getState());
		outageStatus.setPostalCode(stat.getPostalCode());
		outageStatus.setHouseNumber(stat.getHouseNumber());
		outageStatus.setCustomersImpacted(stat.getAffectedCustomers());
		outageStatus.setCause(stat.getCause());
		outageStatus.setOutageStatus(stat.getStatus());
		
		outageStatus.setEsrt(convertToZonedDateTime(stat.getEsrt()));
		outageStatus.setOutageDateTime(convertToZonedDateTime(stat.getStartDate()));
		outageStatus.setRestoredDateTime(convertToZonedDateTime(stat.getRestoredDate()));
		outageStatus.setReportedNotProcessed(stat.isProcessed());
		outageStatus.setLastUpdatedDateTime(convertToZonedDateTime(stat.getLastUpdateDate()));
		
		return outageStatus;
	}
	
	private ZonedDateTime convertToZonedDateTime(String dateTime) {
		return dateTime != null ? LocalDateTime.parse(dateTime, DateTimeFormatter.ISO_DATE_TIME).atZone(ZONE_ID) : null;
	}
	
	@RequestMapping(value = "/dependencyHealth", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> dependencyHealth ()
	{
		try {
			if(OutageStatusConfig.REPORTED_OUTAGES_URL == null || OutageStatusConfig.REPORTED_OUTAGES_URL == "") 
			{
				logger.info("Properties are null! Returning failure from healthcheck...");
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
			}
			
			String url = OutageStatusConfig.configURL + "/health";
			logger.info("Calling config server healthcheck: url {}", url);
				
			//ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
			ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
			return ResponseEntity.status(response.getStatusCode()).body(null);
		} catch (HttpStatusCodeException ex) {
			logger.error("Error calling config server {}", ex.toString());
			return ResponseEntity.status(ex.getStatusCode()).body(null);
		}
	
	
	}
}
