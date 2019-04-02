package com.ameren.status.service;

import java.net.URI;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.ameren.status.config.OutageStatusConfig;
import com.ameren.status.model.OutageDetailWrapper;
import com.ameren.status.model.OutageReportedWrapper;

@Service("OutageStatus")
@RefreshScope
public class OutageStatusServiceImpl implements OutageStatusService {

	private static final Logger logger = LoggerFactory.getLogger(OutageStatusServiceImpl.class);

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public OutageReportedWrapper retrieveOutageReported(String account) {
		ResponseEntity<OutageReportedWrapper> response = null;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Object> httpRequest;
		httpRequest = new HttpEntity<>(headers);
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(String.format(OutageStatusConfig.REPORTED_OUTAGES_URL, account));
		URI uriBuilder = builder.buildAndExpand().toUri();

		try {
			response = restTemplate.exchange(uriBuilder, HttpMethod.GET, httpRequest, OutageReportedWrapper.class);

		} catch (HttpStatusCodeException ex) {
			logger.error("Error Calling Outage Reported Service with Status Code {} {}", ex.getStatusCode(), ex.getResponseBodyAsString());
			OutageReportedWrapper outageReportedWrapper = new OutageReportedWrapper();
			outageReportedWrapper.setStatusCode(ex.getStatusCode().value());
			return outageReportedWrapper;
		}
		return response.getBody();
	}

	public OutageDetailWrapper retrieveOutageDetail(String account) throws HttpStatusCodeException {
		ResponseEntity<OutageDetailWrapper> response = null;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Object> httpRequest;
		httpRequest = new HttpEntity<>(headers);
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(String.format(OutageStatusConfig.OUTAGE_STATUS_URL, account));
		URI uri = builder.buildAndExpand().toUri();

		try {
			logger.info("Retrieving status from: {}", uri.getPath());
			response = restTemplate.exchange(uri, HttpMethod.GET, httpRequest, OutageDetailWrapper.class);

		} catch (HttpStatusCodeException ex) {
			logger.error("Error Calling Outage Detail Service with Status Code {} {} {}", ex.getStatusCode(), ex.getLocalizedMessage(), ex.getResponseBodyAsString());
			ex.printStackTrace();
			throw ex;
		}

		return response.getBody();
	}

}
