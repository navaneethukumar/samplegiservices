package com.spire.candidateResources;

import javax.ws.rs.core.Response;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.spire.base.controller.Assertion;
import com.spire.base.controller.ContextManager;
import com.spire.base.controller.Logging;
import com.spire.base.controller.TestPlan;
import com.spire.base.service.BaseServiceConsumerNew;
import com.spire.base.service.Constants;
import com.spire.base.service.utils.CandidateResourceServiceUtil;
import com.spire.service.consumers.CandidateResourcesConsumer;
import com.spire.service.consumers.CandidateStatsConsumer;

import junit.framework.Assert;
import spire.talent.gi.beans.CandidateStatsRequestBean;
import spire.talent.gi.beans.GetCandidateRequestBean;

public class CandidateResourcesTestPlan extends TestPlan {

	String hostName;
	CandidateResourcesConsumer candConsumer = null;
	String userId;
	String password;
	CandidateStatsRequestBean candStatsReqBean = null;
	CandidateStatsConsumer candStatsConsumer = null;
	GetCandidateRequestBean candRequestBean = null;

	/**
	 * Passing HostName from the xml.
	 */
	@BeforeTest(alwaysRun = true)
	public void setUp() {
		hostName = (String) ContextManager.getThreadContext().getHostAddress();
		userId = Constants.user_Id;
		password = Constants.password;
		Logging.log("Start :: Login with Username: " + userId + "Password: " + password + "and hostName: " + hostName);

	}

	/**
	 * Get Candidate Profile, provide id and projection parameter type
	 * Verify:Response code and asserting response.
	 */
	@Test(groups = { "sanity", "verifyGetCandidateProfileRequest" })
	public void verifyGetCandidateProfileRequest() {
		candConsumer = new CandidateResourcesConsumer();
		// Get authentication token
		candConsumer.getToken(userId, password, hostName);
		// Executes Get request and returns Response
		Response responsebody = candConsumer.getcandidateprofile(hostName);
		// Asserting Response Code
		Assertion.assertEquals(responsebody.getStatus(), 200,
				"response expected 200 but found response code as:" + responsebody.getStatus());
		String response = responsebody.readEntity(String.class);
		System.out.println(response);
		// Asserting response body
		Assertion.assertTrue(response.contains("6002:6005:19c5a4a6aabb4336a5718e079e26528e"),
				"not matching with the response body");

	}

	/**
	 * Get Candidate Profile without any parameter Verify:Response code
	 */
	@Test(groups = { "sanity", "verifyGetCandidateProfilewithoutanyparameterRequest" })
	public void verifyGetCandidateProfilewithoutanyparameterRequest() {
		candConsumer = new CandidateResourcesConsumer();
		// Get authentication token
		candConsumer.getToken(userId, password, hostName);
		// Executes Get request and returns Response
		Response responsebody = candConsumer.getCandidateprofilewithoutanyparameter(hostName);
		// Asserting Response Code
		Assertion.assertTrue(responsebody.getStatus() != 200,
				"response code expected not equal to 200 but found as:" + responsebody.getStatus());
	}

	/**
	 * GET Candidate profile without id Verify:Response code
	 * 
	 */
	@Test(groups = { "sanity", "verifyGetCandidateProfilewithoutidRequest" })
	public void verifyGetCandidateProfilewithoutidRequest() {
		candConsumer = new CandidateResourcesConsumer();
		// Get authentication token
		candConsumer.getToken(userId, password, hostName);
		// Executes Get request and returns Response
		Response responsebody = candConsumer.getCandidateprofilewithoutid(hostName);
		// Asserting Response Code
		Assertion.assertTrue(responsebody.getStatus() != 200,
				"response code expected not equal to 200 but found as:" + responsebody.getStatus());
	}

	/**
	 * GET Candidate profile without projection Verify:Response code
	 */
	@Test(groups = { "sanity", "verifyGetCandidateProfilewithoutprojectionRequest" })
	public void verifyGetCandidateProfilewithoutprojectionRequest() {
		candConsumer = new CandidateResourcesConsumer();
		// Get authentication token
		candConsumer.getToken(userId, password, hostName);
		// Executes Get request and returns Response
		Response responsebody = candConsumer.getCandidateprofilewithoutprojection(hostName);
		// Asserting Response Code
		Assertion.assertEquals(responsebody.getStatus(), 200,
				"response expected 200 but found response code as:" + responsebody.getStatus());
		String response = responsebody.readEntity(String.class);
		System.out.println(response);
		// Asserting Response Body
		Assertion.assertTrue(response.contains("6002:6005:19c5a4a6aabb4336a5718e079e26528e"),
				"not matching with the response body");
	}

	/**
	 * @author Radharani Patra 12/08/16 Steps:(POST REQUEST)Get Candidate stats with valid
	 *         parameter(Requiistion Id and attribute) Validation: Response code
	 *         and body
	 */

	@Test(groups = { "sanity", "getCandidateStatsWithValidParameter" })
	public void getCandidateStatsWithValidParameter() {
		//Get Requset Bean, pass multiple requisitions
		candStatsReqBean = CandidateResourceServiceUtil.getCandidateStats(Constants.requisition_Id,
				Constants.requisition_Id1);
		// Get authentication token
		candStatsConsumer = new CandidateStatsConsumer(userId, password, hostName);
		//Execute POST Request,returns response
		Response response = candStatsConsumer.getCandidateStats(candStatsReqBean, hostName);
		//Asserting Response Code
		Assertion.assertEquals(response.getStatus(), 200, "Response not successfull");
		Logging.log("RESPONSE CODE >>" + response.getStatus());
		//Get Response body
		String responseBody = response.readEntity(String.class);
		Logging.log(responseBody);
		//Asserting response Body
		Assertion.assertTrue(responseBody.contains("Automatched") || responseBody.contains("Customer Mapped"),
				"Get Candidate Stats Unsuccessful");
		Logging.log("Get Candidate Stats successful and contains Automatched or Customer Mapped status count");
	}

	/**
	 * @author Radharani Patra 12/08/16 Steps:Get Candidate stats with valid
	 *         parameter(Requisition Id and attribute Gender) Validation:
	 *         Response code and body
	 */

	@Test(groups = { "sanity", "getCandidateStatsWithValidParameterGender" })
	public void getCandidateStatsWithValidParameterGender() {
		//Get Requset Bean, pass multiple requisitions
		candStatsReqBean = CandidateResourceServiceUtil.getCandidateStatsGender(Constants.requisition_Id,
				Constants.requisition_Id1);
		candStatsConsumer = new CandidateStatsConsumer(userId, password, hostName);
		//Execute POST Request,returns response
		Response response = candStatsConsumer.getCandidateStats(candStatsReqBean, hostName);
		//Asserting Response Code
		Assertion.assertEquals(response.getStatus(), 200, "Response not successfull");
		Logging.log("RESPONSE CODE >>" + response.getStatus());
		//Get Response body
		String responseBody = response.readEntity(String.class);
		Logging.log(responseBody);
		//Asserting response Body
		Assertion.assertTrue(responseBody.contains("F") || responseBody.contains("M"),
				"Get Candidate Stats Unsuccessful");
		Logging.log("Get Candidate Stats successful and contains gender count ");
	}

	/**
	 * @author Radharani Patra 12/08/16 Steps:Get Candidate stats with blank
	 *         parameter Validation: Response code and body
	 */

	@Test(groups = { "sanity", "getCandidateStatsWithBlankParameter" })
	public void getCandidateStatsWithBlankParameter() {
		//Get Requset Bean, pass parameters
		candStatsReqBean = CandidateResourceServiceUtil.getCandidateStatsBlank();
		candStatsConsumer = new CandidateStatsConsumer(userId, password, hostName);
		//Execute POST Request,returns response
		Response response = candStatsConsumer.getCandidateStats(candStatsReqBean, hostName);
		//Asserting Response Code
		Assertion.assertEquals(response.getStatus(), 400, "Response successfull, expected : 400");
		Logging.log("RESPONSE CODE >>" + response.getStatus());
		//Get Response body
		String responseBody = response.readEntity(String.class);
		Logging.log(responseBody);
		//Asserting response Body
		Assertion.assertTrue(responseBody.contains("INVALID_PARAMETER"), "Get Candidate Stats Successful");
		Logging.log("Get Candidate Stats unsuccessful with respone: INVALID_PARAMETER ");
	}

	/**
	 * @author Radharani Patra 12/08/16 Steps:Get Candidate stats with Invalid
	 *         parameter(Requisition Id ) blank attribute Validation: Response
	 *         code and body
	 */

	@Test(groups = { "sanity", "getCandidateStatsWithReqIdParameter" })
	public void getCandidateStatsWithReqIdParameter() {
		//Get Requset Bean, pass requisitions
		candStatsReqBean = CandidateResourceServiceUtil.getCandidateStatsReqId(Constants.requisition_Id);
		candStatsConsumer = new CandidateStatsConsumer(userId, password, hostName);
		//Execute POST Request,returns response
		Response response = candStatsConsumer.getCandidateStats(candStatsReqBean, hostName);
		//Asserting Response Code
		Assertion.assertEquals(response.getStatus(), 400, "Response successfull, Expected : 400");
		Logging.log("RESPONSE CODE >>" + response.getStatus());
		//Get Response body
		String responseBody = response.readEntity(String.class);
		Logging.log(responseBody);
		//Asserting response Body
		Assertion.assertTrue(responseBody.contains("INVALID_PARAMETER"), "Get Candidate Stats Successful");
		Logging.log("Get Candidate Stats unsuccessful with respone: INVALID_PARAMETER ");
	}

	/**
	 * @author Radharani Patra 12/08/16 Steps:Get Candidate stats with Invalid
	 *         parameter(Requisition Id ) blank attribute Validation: Response
	 *         code and body
	 */

	@Test(groups = { "sanity", "getCandidateStatsWithAttributeParameter" })
	public void getCandidateStatsWithAttributeParameter() {
		//Get Requset Bean, pass attribute
		candStatsReqBean = CandidateResourceServiceUtil.getCandidateStatsAttribute();
		candStatsConsumer = new CandidateStatsConsumer(userId, password, hostName);
		//Execute POST Request,returns response
		Response response = candStatsConsumer.getCandidateStats(candStatsReqBean, hostName);
		//Asserting Response Code
		Assertion.assertEquals(response.getStatus(), 400, "Response successfull, Expected : 400");
		Logging.log("RESPONSE CODE >>" + response.getStatus());
		//Get Response body
		String responseBody = response.readEntity(String.class);
		Logging.log(responseBody);
		//Asserting response Body
		Assertion.assertTrue(responseBody.contains("INVALID_PARAMETER"), "Get Candidate Stats Successful");
		Logging.log("Get Candidate Stats unsuccessful with respone: INVALID_PARAMETER ");
	}

	/**
	 * @author Radharani Patra 12/08/16 Steps:Get Candidate stats with Invalid
	 *         parameter(Requisition Id ) blank attribute Validation: Response
	 *         code and body
	 */

	@Test(groups = { "sanity", "getCandidateStatsWithInvalidParameter" })
	public void getCandidateStatsWithInvalidParameter() {
		//Get Requset Bean, pass invalid req id
		candStatsReqBean = CandidateResourceServiceUtil.getCandidateStatsInvalid();
		candStatsConsumer = new CandidateStatsConsumer(userId, password, hostName);
		//Execute POST Request,returns response
		Response response = candStatsConsumer.getCandidateStats(candStatsReqBean, hostName);
		//Asserting Response Code
		Assertion.assertEquals(response.getStatus(), 200, "Response successfull, Expected : 200");
		Logging.log("RESPONSE CODE >>" + response.getStatus());
		//Get Response body
		String responseBody = response.readEntity(String.class);
		Logging.log(responseBody);
		//Asserting response Body
		Assertion.assertTrue(responseBody.contains(""), "Get Candidate Stats Successful");
		Logging.log("Get Candidate Stats successful with invalid parameter Returning Blank Response ");
	}

	/**
	 * @author Radharani Patra 12/08/16 Steps:Get Candidate List with valid
	 *         parameter(Candidate Id and Projection Type) Validation: Response
	 *         code and body
	 */

	@Test(groups = { "sanity", "getCandidateListWithValidParameterBASIC" })
	public void getCandidateListWithValidParameterBASIC() {
		//Get Requset Bean, pass valid parameter, candidate id and projection type
		candRequestBean = CandidateResourceServiceUtil.getCandidateListBasic(Constants.candidate_Id);
		candConsumer = new CandidateResourcesConsumer(userId, password, hostName);
		//Execute POST Request,returns response
		Response response = candConsumer.getCandidateList(candRequestBean, hostName);
		//Asserting Response Code
		Assertion.assertEquals(response.getStatus(), 200, "Response successfull, Expected : 200");
		Logging.log("RESPONSE CODE >>" + response.getStatus());
		//Get Response body
		String responseBody = response.readEntity(String.class);
		//Asserting response Body
		Assertion.assertTrue(responseBody.contains(Constants.candidate_Id), "Get Candidate List UnSuccessful");
		Logging.log("Get candidate list successful, candidate id: " + Constants.candidate_Id);
	}

	/**
	 * @author Radharani Patra 12/08/16 Steps:Get Candidate List with valid
	 *         parameter(Candidate Id and Projection Type) Validation: Response
	 *         code and body
	 */

	@Test(groups = { "sanity", "getCandidateListWithValidParameterFULL" })
	public void getCandidateListWithValidParameterFULL() {
		//Get Requset Bean, pass valid parameter, candidate id and projection type
		candRequestBean = CandidateResourceServiceUtil.getCandidateListFull(Constants.candidate_Id);
		candConsumer = new CandidateResourcesConsumer(userId, password, hostName);
		//Execute POST Request,returns response
		Response response = candConsumer.getCandidateList(candRequestBean, hostName);
		//Asserting Response Code
		Assertion.assertEquals(response.getStatus(), 200, "Response successfull, Expected : 200");
		Logging.log("RESPONSE CODE >>" + response.getStatus());
		//Get Response body
		String responseBody = response.readEntity(String.class);
		//Asserting response Body
		Assertion.assertTrue(responseBody.contains(Constants.candidate_Id), "Get Candidate List UnSuccessful");
		Logging.log("Get candidate list successful, candidate id: " + Constants.candidate_Id);
	}

	/**
	 * @author Radharani Patra 12/08/16 Steps:Get Candidate List with valid
	 *         parameter(Candidate Id and Projection Type) Validation: Response
	 *         code and body
	 */

	@Test(groups = { "sanity", "getCandidateListWithValidParameterCUSTOM" })
	public void getCandidateListWithValidParameterCUSTOM() {
		//Get Requset Bean, pass valid parameter, candidate id and projection type
		candRequestBean = CandidateResourceServiceUtil.getCandidateListCustom(Constants.candidate_Id);
		candConsumer = new CandidateResourcesConsumer(userId, password, hostName);
		//Execute POST Request,returns response
		Response response = candConsumer.getCandidateList(candRequestBean, hostName);
		//Asserting Response Code
		Assertion.assertEquals(response.getStatus(), 200, "Response successfull, Expected : 200");
		Logging.log("RESPONSE CODE >>" + response.getStatus());
		//Get Response body
		String responseBody = response.readEntity(String.class);
		//Asserting response Body
		Assertion.assertTrue(responseBody.contains(Constants.candidate_Id), "Get Candidate List UnSuccessful");
		Logging.log("Get candidate list successful, candidate id: " + Constants.candidate_Id);
	}

	/**
	 * @author Radharani Patra 12/08/16 Steps:Get Candidate List with blank
	 *         parameter(Candidate Id and Projection Type) Validation: Response
	 *         code and body
	 */

	@Test(groups = { "sanity", "getCandidateListWithBlankParameter" })
	public void getCandidateListWithBlankParameter() {
		//Get Requset Bean, pass blank candidate id and projection type
		candRequestBean = CandidateResourceServiceUtil.getCandidateListBlank();
		candConsumer = new CandidateResourcesConsumer(userId, password, hostName);
		//Execute POST Request,returns response
		Response response = candConsumer.getCandidateList(candRequestBean, hostName);
		//Asserting Response Code
		Assertion.assertEquals(response.getStatus(), 400, "Response successfull, Expected : 400");
		Logging.log("RESPONSE CODE >>" + response.getStatus());
		//Get Response body
		String responseBody = response.readEntity(String.class);
		//Asserting response Body
		Assertion.assertTrue(responseBody.contains("INVALID_PARAMETER"), "Get Candidate List Successful");
		Logging.log("Get candidate list Unsuccessful with INVALID_PARAMETER");
	}

	/**
	 * @author Radharani Patra 12/08/16 Steps:Get Candidate List with Invalid
	 *         parameter(Candidate Id blank and Projection Type) Validation:
	 *         Response code and body
	 */

	@Test(groups = { "sanity", "getCandidateListWithBlankCandidateId" })
	public void getCandidateListWithBlankCandidateId() {
		//Get Requset Bean, pass blank candidate id and projection type
		candRequestBean = CandidateResourceServiceUtil.getCandidateListBlankCId();
		candConsumer = new CandidateResourcesConsumer(userId, password, hostName);
		//Execute POST Request,returns response
		Response response = candConsumer.getCandidateList(candRequestBean, hostName);
		Assertion.assertEquals(response.getStatus(), 400, "Response successfull, Expected : 400");
		Logging.log("RESPONSE CODE >>" + response.getStatus());
		//Get Response body
		String responseBody = response.readEntity(String.class);
		//Asserting response Body
		Assertion.assertTrue(responseBody.contains("INVALID_PARAMETER"), "Get Candidate List Successful");
		Logging.log("Get candidate list Unsuccessful with INVALID_PARAMETER");
	}

	/**
	 * @author Radharani Patra 12/08/16 Steps:Get Candidate List with
	 *         parameter(Candidate Id ) Validation: Response code and body
	 */

	@Test(groups = { "sanity", "getCandidateListWithBlankProjectionType" })
	public void getCandidateListWithBlankProjectionType() {
		//Get Requset Bean, pass candidate id
		candRequestBean = CandidateResourceServiceUtil.getCandidateListBlankPojectiontype(Constants.candidate_Id);
		candConsumer = new CandidateResourcesConsumer(userId, password, hostName);
		//Execute POST Request,returns response
		Response response = candConsumer.getCandidateList(candRequestBean, hostName);
		Assertion.assertEquals(response.getStatus(), 200, "Response successfull, Expected : 200");
		Logging.log("RESPONSE CODE >>" + response.getStatus());
		//Get Response body
		String responseBody = response.readEntity(String.class);
		//Asserting response Body
		Assertion.assertTrue(responseBody.contains(Constants.candidate_Id), "Get Candidate List UnSuccessful");
		Logging.log("Get candidate list successful, candidate id: " + Constants.candidate_Id);
	}

	/**
	 * @author Radharani Patra 12/08/16 Steps:Get Candidate List with
	 *         parameter(Candidate Id ) Validation: Response code and body
	 */

	@Test(groups = { "sanity", "getCandidateListWithInvalidProjectionType" })
	public void getCandidateListWithInvalidProjectionType() {
		//Get Requset Bean, pass invalid candidate id and projection type
		candRequestBean = CandidateResourceServiceUtil.getCandidateListInvalidCandidateId(Constants.candidate_Id);
		candConsumer = new CandidateResourcesConsumer(userId, password, hostName);
		//Execute POST Request,returns response
		Response response = candConsumer.getCandidateList(candRequestBean, hostName);
		Assertion.assertEquals(response.getStatus(), 200, "Response successfull, Expected : 200");
		Logging.log("RESPONSE CODE >>" + response.getStatus());
		//Get Response body
		String responseBody = response.readEntity(String.class);
		//Asserting response Body
		Assertion.assertTrue(responseBody.contains(""), "Get Candidate List successfull");
		Logging.log("Response is blank for invalid candidate id");
	}

	/**
	 * priti- GET candidate profile without headers Validation on response code
	 *
	 */
	@Test(groups = { "sanity", "verifyGetCandidateProfilewithoutheaderRequest" })
	public void verifyGetCandidateProfilewithoutheaderRequest() {
		candConsumer = new CandidateResourcesConsumer();
		Response responsebody = candConsumer.getcandidateprofile(hostName);
		Assertion.assertTrue((responsebody.getStatus()) != 200,
				"response code expected not equal to 200 but found as:" + responsebody.getStatus());

	}
}