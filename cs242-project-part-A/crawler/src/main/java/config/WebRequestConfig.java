/**
 * 
 */
package config;

/**
 * @author sattu
 *
 */
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;

public class WebRequestConfig {

	public WebRequestConfig() {
		// TODO Auto-generated constructor stub
	}

	private String URL;

	public void setURL(String URL) {
		this.URL = URL;
	}

	public String getURL() {
		return this.URL;
	}

	private Map<String, String> header = new HashMap<String, String>();

	public void setHeader(String key, String value) {
		header = new HashMap<String, String>();
		header.put(key, value);
	}

	public void addHeader(String key, String value) {
		header.put(key, value);
	}

	public String getHeaders() {
		JSONObject e = new JSONObject();
		for (Map.Entry<String, String> entry : header.entrySet()) {
			e.put(entry.getKey(), entry.getValue());
		}
		return e.toString();
	}

	public Map<String, String> getHeader() {
		return header;
	}

	public String body;

	public void setBody(String body) {
		this.body = body;
	}

	public String getBody() {
		return this.body;
	}
}
