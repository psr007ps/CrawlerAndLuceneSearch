/**
 * 
 */
package config;

import java.util.ArrayList;

/**
 * @author sattu
 *
 */
public class TwitterPaginationConfig {

	ArrayList<TwitterDataConfig> twitterDataConfigList = null;
	String nextToken = null;

	/**
	 * 
	 */
	public TwitterPaginationConfig() {
		// TODO Auto-generated constructor stub
	}

	public void setTwitterData(ArrayList<TwitterDataConfig> twitterDataConfigList) {
		this.twitterDataConfigList = twitterDataConfigList;
	}

	public ArrayList<TwitterDataConfig> getTwitterData() {
		return this.twitterDataConfigList;
	}

	public void setToken(String token) {
		this.nextToken = token;
	}

	public String getToken() {
		return this.nextToken;
	}

}
