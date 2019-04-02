package com.ameren.status.service;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.net.URI;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.ameren.status.model.Outage;
import com.ameren.status.model.OutageDetailWrapper;

//@RunWith(SpringRunner.class)
//@SpringBootTest(properties = { "configRepo=outagestatusapi.development",
//		"configURL=http://localhost:8890/outageconfigserver/api", "region=us-east-1" })
/**
 * This integration test is for verifying that outage data from CSS is retrived correctly.
 * This also serves as a sample for future integration testing.  This is, however, ignored
 * because of lack of good deployment pipeline.  This test should ideally run in QA.  Currently (4/12/2018)
 * our deployment pipeline doesn't have the capability to run any tests in QA.
 * 
 * To run this test locally, uncomment the @RunWith, @SpringBootTest and @Autowired.  
 * Also, run the config server locally in order for this to work.
 */
public class OutageStatusServiceImplIntegrationTest {

//	@Autowired
	private OutageStatusServiceImpl service;

	@Ignore
	@Test
	public void ensureDataIsMappedCorrectly() {
		String muleSoftEndPointForReportingOutage = "https://hawscorpd.ameren.com:8443/css/supp/outage/v1/reportedOutages";

		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(muleSoftEndPointForReportingOutage);
		URI uri = builder.buildAndExpand().toUri();

		String payload = "{\"billAccount\": \"6623201117\",\"requestSource\": \"VALUE\", \"requestedBy\": \"SOMEVAL\"}";

		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> httpRequest = new HttpEntity<>(payload, headers);

		ResponseEntity<String> responseEntity = restTemplate.exchange(uri, HttpMethod.POST, httpRequest, String.class);

		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());

		OutageDetailWrapper outageDetail;
		do {
			outageDetail = service.retrieveOutageDetail("6623201117");

		} while (outageDetail.getData() == null);

		Outage outage = outageDetail.getData().getOutage();
		assertThat(outage.getBillAccountNumber(), is(not(isEmptyOrNullString())));
		assertThat(outage.getState(), is(not(isEmptyOrNullString())));
		assertThat(outage.getPostalCode(), is(not(isEmptyOrNullString())));
		assertThat(outage.getHouseNumber(), is(not(isEmptyOrNullString())));
		assertThat(outage.getAffectedCustomers(), is(greaterThanOrEqualTo(1)));
		assertThat(outage.getEsrt(), is(not(isEmptyOrNullString())));
		assertThat(outage.getPremiseNumber(), is(not(isEmptyOrNullString())));
		assertThat(outage.getCompanyCode(), is(greaterThanOrEqualTo(1)));
		assertThat(outage.getPhoneNumber(), is(not(isEmptyOrNullString())));
		assertThat(outage.getTransformerId(), is(not(isEmptyOrNullString())));
		assertThat(outage.getStatus(), is(not(isEmptyOrNullString())));
		assertThat(outage.getCause(), is(not(isEmptyOrNullString())));
		assertThat(outage.getStartDate(), is(not(isEmptyOrNullString())));
		assertThat(outage.getEsrt(), is(not(isEmptyOrNullString())));
		assertThat(outage.getAffectedCustomers(), is(greaterThan(0)));
		assertThat(outage.getRestoredDate(), is(not(isEmptyOrNullString())));
		assertThat(outage.isPowerOn(), is(notNullValue()));
		assertThat(outage.isProcessed(), is(notNullValue()));
		assertThat(outage.getLastUpdateDate(), is(not(isEmptyOrNullString())));		
	}
}
