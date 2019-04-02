package com.ameren.status.api;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.ameren.status.config.OutageStatusConfig;
import com.ameren.status.model.Outage;
import com.ameren.status.model.OutageDetail;
import com.ameren.status.model.OutageDetailWrapper;
import com.ameren.status.model.OutageReported;
import com.ameren.status.model.OutageReportedCalls;
import com.ameren.status.model.OutageReportedWrapper;
import com.ameren.status.service.OutageStatusService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@SpringBootTest(properties = 
					{"configRepo = outagestatusapi.development", 
							"configURL = https://dev-outage.ece.ameren.com/outageconfigserver/api", 
							"region = us-east-1"})
@RunWith(SpringJUnit4ClassRunner.class)
@AutoConfigureMockMvc
public class OutageStatusControllerRestTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private OutageStatusService outageStatusService;

	private ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
	
	@Test
	public void statusRequestRestEndpoint_UnknownAndReportedOutage() throws Exception
	{
		String account = "2133992787";
		
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

		mockMvc.perform(get("/accounts/2133992787"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.*", Matchers.hasSize(8)))
			.andExpect(jsonPath("$.knownOutage").value(false))
			.andExpect(jsonPath("$.reported").value(true));
	}
	
	@Test
	public void statusRequestRestEndpoint_outageServiceDetailReturns200_forInformational_AccountNotAvailable() throws Exception
	{
		String account = "0123456789";
		OutageStatusConfig.GENERIC_ERROR_MESSAGE = "Unable to process request.";
		
		OutageDetailWrapper odw = mapper.readValue(this.getClass().getResourceAsStream("/outage-status-response-200-no-account-found.json"), OutageDetailWrapper.class);
		
		// mock service calls
		when(outageStatusService.retrieveOutageDetail(account)).thenReturn(odw);

		mockMvc.perform(get("/accounts/0123456789"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.message").value("Message returned from CSS REST API (SUPP) is Informational The account number submitted for this request is not available. Please contact us if you have a question."))
			.andExpect(jsonPath("$.data").doesNotExist());
	}
	
	@Test
	public void statusRequestRestEndpoint_outageServiceDetailReturns200_forInformational_AccountGasOnly() throws Exception
	{
		String account = "0123456789";
		OutageStatusConfig.GENERIC_ERROR_MESSAGE = "Unable to process request.";
		
		OutageDetailWrapper odw = mapper.readValue(this.getClass().getResourceAsStream("/outage-status-response-200-no-electric-service.json"), OutageDetailWrapper.class);
		
		// mock service calls
		when(outageStatusService.retrieveOutageDetail(account)).thenReturn(odw);

		mockMvc.perform(get("/accounts/0123456789"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.message").value("Message returned from CSS REST API (SUPP) is Informational This location does not have electric service."))
			.andExpect(jsonPath("$.data").doesNotExist());
	}
	
}
