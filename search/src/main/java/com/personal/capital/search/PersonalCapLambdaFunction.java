package com.personal.capital.search;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import org.apache.sling.commons.json.JSONObject;
import org.springframework.web.client.RestTemplate;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class PersonalCapLambdaFunction implements RequestHandler<SearchRequest, JSONObject> {

	private static String url = "https://search-personal-capital-cgujz27zdiz6vvxs37mnyub5vq.us-east-1.es.amazonaws.com/personal-capital/_search?q=";

	@Override
	public JSONObject handleRequest(SearchRequest request, Context context) {
		LambdaLogger logger = context.getLogger();

		if (request.planName != null) {
			try {
				url += "PLAN_NAME:\"" + URLEncoder.encode(request.planName, "UTF-8") + "\"";
			} catch (UnsupportedEncodingException e) {
				logger.log("Exception occured while encoding planName in the url : " + e.getLocalizedMessage());
			}
		}

		if (request.sponsorName != null) {
			try {
				url += "SPONSOR_DFE_NAME:\"" + URLEncoder.encode(request.sponsorName, "UTF-8") + "\"";
			} catch (UnsupportedEncodingException e) {
				logger.log("Exception occured while encoding sponsorName in the url : " + e.getLocalizedMessage());
			}

		}
		if (request.sponsorState != null) {
			try {
				url += "SPONS_DFE_MAIL_US_STATE:\"" + URLEncoder.encode(request.sponsorState, "UTF-8") + "\"";
			} catch (UnsupportedEncodingException e) {
				logger.log("Exception occured while encoding the sponsorState in the url : " + e.getLocalizedMessage());
			}
		}

		RestTemplate restTemplate = new RestTemplate();
		JSONObject json = null;
		try {
			String response = restTemplate.getForObject(url, String.class);
			json = new JSONObject(response);
		} catch (Exception e) {
			logger.log("Lambda Function call failed " + "ErrorMessage= " + e.getLocalizedMessage() + "url= " + url);
		}
		return json;
	}
}
