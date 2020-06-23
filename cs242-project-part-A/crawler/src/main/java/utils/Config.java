/**
 * 
 */
package utils;

import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * @author sattu
 *
 */
public class Config {

	/**
	 * 
	 */
	public Config() {
		// TODO Auto-generated constructor stub
	}

	JSONObject configJSON = null;

	public Config(String configFile) throws IOException, ParseException {
		FileReader fileReader = null;
		try {
			fileReader = new FileReader(configFile);
			configJSON = (JSONObject) new JSONParser().parse(fileReader);
		} finally {
			if (fileReader != null) {
				fileReader.close();
			}
		}
	}

	public Object getValueForKey(String key) {
		return configJSON.get(key);
	}

	public JSONObject getJSONObject() {
		return configJSON;
	}

}
