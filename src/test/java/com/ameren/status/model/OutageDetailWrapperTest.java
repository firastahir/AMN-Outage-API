package com.ameren.status.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@RunWith(MockitoJUnitRunner.class)
public class OutageDetailWrapperTest {
	
	private final String account = "2133992787";
	private ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
	
	@Test
	public void outageDetailWrapper_mapStatusDetail_noPower_Response_returnOutageDetailWrapper() throws Exception {
		//arrange
		
		//act
		OutageDetailWrapper odw = mapper.readValue(this.getClass().getResourceAsStream("/outage-status-response-200-no-power.json"), OutageDetailWrapper.class);
		
		//assert
		assertThat(odw).isNotNull();
		assertThat(odw.getData()).isNotNull();
		assertThat(odw.getStatusCode()).isNotNull();
		assertThat(odw.getStatusCode()).isEqualTo(200);
		assertThat(odw.isHasError()).isFalse();
		assertThat(odw.getMessage()).isNotEmpty();
		assertThat(odw.getMessage()).isEqualTo("CSS REST API Call was successful (SUPP)");
		assertThat(odw.getData().getOutage()).isNotNull();
		assertThat(odw.getData().getOutage().isPowerOn()).isFalse();
		assertThat(odw.getData().getOutage().getBillAccountNumber()).isEqualTo("0000012345");
		assertThat(odw.getData().getOutage().getPremiseNumber()).isEqualTo("1234");
		assertThat(odw.getData().getOutage().getState()).isEqualTo("IL");
		assertThat(odw.getData().getOutage().getPostalCode()).isEqualTo("12345");
		assertThat(odw.getData().getOutage().getHouseNumber()).isEqualTo("123");
		assertThat(odw.getData().getOutage().getPhoneNumber()).isEqualTo("6185551234");
		assertThat(odw.getData().getOutage().getTransformerId()).isEqualTo("11111111111");
		assertThat(odw.getData().getOutage().getStatus()).isEqualTo("Order has been received");
		assertThat(odw.getData().getOutage().getCause()).isEqualTo("We are assessing the cause.");
		assertThat(odw.getData().getOutage().getStartDate()).isEqualTo("2019-03-15T03:45:00");
		assertThat(odw.getData().getOutage().getEsrt()).isNotEmpty();
		assertThat(odw.getData().getOutage().getEsrt()).isEqualTo("0001-01-01T00:00:00");
		assertThat(odw.getData().getOutage().getOriginalEstimatedResorationDateTime()).isNotEmpty();
		assertThat(odw.getData().getOutage().getOriginalEstimatedResorationDateTime()).isEqualTo("2019-03-15T06:00:00");
		assertThat(odw.getData().getOutage().getAffectedCustomers()).isEqualTo(1);
		assertThat(odw.getData().getOutage().getRestoredDate()).isEqualTo("0001-01-01T00:00:00");
		assertThat(odw.getData().getOutage().getLastUpdateDate()).isEqualTo("2019-03-19T17:54:17.27");
		assertThat(odw.getData().getOutage().getCallBackNumber()).isEqualTo("2175556789");
		assertThat(odw.getData().getOutage().isReportAlert()).isTrue();
		assertThat(odw.getData().getOutage().isProcessed()).isFalse();
		
	}
	
	@Test
	public void outageDetailWrapper_mapStatusDetail_noAccount_Response_returnOutageDetailWrapper() throws Exception {
		//arrange
		
		//act
		OutageDetailWrapper odw = mapper.readValue(this.getClass().getResourceAsStream("/outage-status-response-200-no-account-found.json"), OutageDetailWrapper.class);
		
		//assert
		assertThat(odw).isNotNull();
		assertThat(odw.isHasError()).isTrue();
		assertThat(odw.getStatusCode()).isNotNull();
		assertThat(odw.getStatusCode()).isEqualTo(300);
		assertThat(odw.getMessage()).isNotEmpty();
		assertThat(odw.getMessage()).isEqualTo("Message returned from CSS REST API (SUPP) is Informational The account number submitted for this request is not available. Please contact us if you have a question.");
		assertThat(odw.getData()).isNull();
/*		
		assertThat(odw.getData().getOutage()).isNotNull();
		assertThat(odw.getData().getOutage().isPowerOn()).isFalse();
		assertThat(odw.getData().getOutage().getBillAccountNumber()).isEqualTo("0000012345");
		assertThat(odw.getData().getOutage().getPremiseNumber()).isEqualTo("1234");
		assertThat(odw.getData().getOutage().getState()).isEqualTo("IL");
		assertThat(odw.getData().getOutage().getPostalCode()).isEqualTo("12345");
		assertThat(odw.getData().getOutage().getHouseNumber()).isEqualTo("123");
		assertThat(odw.getData().getOutage().getPhoneNumber()).isEqualTo("6185551234");
		assertThat(odw.getData().getOutage().getTransformerId()).isEqualTo("11111111111");
		assertThat(odw.getData().getOutage().getStatus()).isEqualTo("Order has been received");
		assertThat(odw.getData().getOutage().getCause()).isEqualTo("We are assessing the cause.");
		assertThat(odw.getData().getOutage().getStartDate()).isEqualTo("2019-03-15T03:45:00");
		assertThat(odw.getData().getOutage().getEsrt()).isNotEmpty();
		assertThat(odw.getData().getOutage().getEsrt()).isEqualTo("0001-01-01T00:00:00");
		assertThat(odw.getData().getOutage().getOriginalEstimatedResorationDateTime()).isNotEmpty();
		assertThat(odw.getData().getOutage().getOriginalEstimatedResorationDateTime()).isEqualTo("2019-03-15T06:00:00");
		assertThat(odw.getData().getOutage().getAffectedCustomers()).isEqualTo(1);
		assertThat(odw.getData().getOutage().getRestoredDate()).isEqualTo("0001-01-01T00:00:00");
		assertThat(odw.getData().getOutage().getLastUpdateDate()).isEqualTo("2019-03-19T17:54:17.27");
		assertThat(odw.getData().getOutage().getCallBackNumber()).isEqualTo("2175556789");
		assertThat(odw.getData().getOutage().isReportAlert()).isTrue();
		assertThat(odw.getData().getOutage().isProcessed()).isFalse();
*/		
	}
	
	@Test
	public void outageDetailWrapper_validateIsKnownOutageTrue() {		
		//mock outage details
		OutageDetailWrapper outageDetailWrapper = new OutageDetailWrapper();
		OutageDetail outageDetail = new OutageDetail();
		Outage outage = new Outage();
		outage.setPowerOn(false);
		outage.setBillAccountNumber(account);
		outage.setAffectedCustomers(10);
		outageDetail.setOutage(outage);
		outageDetailWrapper.setData(outageDetail);
		outageDetailWrapper.setHasError(false);
		outageDetailWrapper.setMessage("");
		outageDetailWrapper.setStatusCode(200);
		
		assertNotNull(outageDetailWrapper.getData());
		assertNotNull(outageDetailWrapper.getData().getOutage());
		assertFalse(outageDetailWrapper.getData().getOutage().isPowerOn());
		assertEquals(10, outageDetailWrapper.getData().getOutage().getAffectedCustomers());
		assertTrue(outageDetailWrapper.isKnownOutage());
	}
	
	@Test
	public void outageDetailWrapper_validateIsKnownOutageFalse() {		
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
		
		assertNotNull(outageDetailWrapper.getData());
		assertNotNull(outageDetailWrapper.getData().getOutage());
		assertTrue(outageDetailWrapper.getData().getOutage().isPowerOn());
		assertEquals(0, outageDetailWrapper.getData().getOutage().getAffectedCustomers());
		assertFalse(outageDetailWrapper.isKnownOutage());
	}
	
}
