package com.ameren.status.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OutageStatusConfig {
	public static String REPORTED_OUTAGES_URL;
	public static String OUTAGE_STATUS_URL;
	public static String GENERIC_ERROR_MESSAGE;
	public static String configURL;
	
	@Autowired
	public OutageStatusConfig(@Value("${REPORTED_OUTAGES_URL:}") String REPORTED_OUTAGES_URL,
								@Value("${OUTAGE_STATUS_URL:}") String OUTAGE_STATUS_URL,
								@Value("${GENERIC_ERROR_MESSAGE:}") String GENERIC_ERROR_MESSAGE,
								@Value("${configURL:}") String configURL)
	{
		OutageStatusConfig.REPORTED_OUTAGES_URL = REPORTED_OUTAGES_URL;
		OutageStatusConfig.OUTAGE_STATUS_URL = OUTAGE_STATUS_URL;
		OutageStatusConfig.GENERIC_ERROR_MESSAGE = GENERIC_ERROR_MESSAGE;
		OutageStatusConfig.configURL = configURL;
		
		System.out.println("================== " + OutageStatusConfig.REPORTED_OUTAGES_URL + "================== ");
		System.out.println("================== " + OutageStatusConfig.OUTAGE_STATUS_URL + "================== ");
		System.out.println("================== " + OutageStatusConfig.GENERIC_ERROR_MESSAGE + "================== ");
		System.out.println("================== " + OutageStatusConfig.configURL + "================== ");
	}

}
