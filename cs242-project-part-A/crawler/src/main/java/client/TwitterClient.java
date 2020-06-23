/**
 * 
 */
package client;

import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import config.WebRequestConfig;
import response.Response;
import utils.WebClient;
import utils.Config;

/**
 * @author sattu
 *
 */
public class TwitterClient {

	String bearerToken = null;
	String keyword = null;
	String apiURL = null;
	String location = null;
	String longitude = null;
	String latitude = null;
	String radius = "23mi";
	Long maxResults = null;

	/**
	 * @throws ParseException
	 * @throws IOException
	 * 
	 */
	public TwitterClient() throws IOException, ParseException {
		// TODO Auto-generated constructor stub
		Config config = new Config("/Users/sattu/eclipse-workspace/crawler/appConfig.json");
		bearerToken = (String) config.getValueForKey("bearerToken");
		keyword = (String) config.getValueForKey("keyword");
		apiURL = (String) config.getValueForKey("apiURL");
		location = (String) config.getValueForKey("location");
		longitude = (String) config.getValueForKey("longitude");
		latitude = (String) config.getValueForKey("latitude");
		maxResults = (Long) config.getValueForKey("maxResults");
	}

	public Response getTwitterData(String token) {
		Response response = null;
		WebClient webClient = new WebClient();
		WebRequestConfig webRequestConfig = createRequestConfig(token);
		try {
			response = webClient.doPost(webRequestConfig);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(":::: ERROR : error occurred in getTwitterData() ::::");
			e.printStackTrace();
		}
		return response;
	}

	public WebRequestConfig createRequestConfig(String token) {
		WebRequestConfig webRequestConfig = new WebRequestConfig();
		webRequestConfig.setURL(this.apiURL);
		webRequestConfig.addHeader("authorization", "Bearer " + this.bearerToken);
		webRequestConfig.addHeader("header", "application/json");
		webRequestConfig.setBody(createRequestBody(token).toString());
		return webRequestConfig;
	}

	public JSONObject createRequestBody(String token) {
		JSONObject jsonObject = new JSONObject();
		// String body = "{ \"query\": \"\"nike\" lang:en point_radius:["+
		// this.longitude + " " + this.latitude + " " + this.radius]\"}";
		String query = "lang:en point_radius:[" + this.longitude + " " + this.latitude + " " + this.radius
				+ "]";
		jsonObject.put("query", query);
		jsonObject.put("maxResults", this.maxResults);
		if (token != null) {
			jsonObject.put("next", token);
		}
		return jsonObject;
	}

}
