/**
 * 
 */
package response;

/**
 * @author sattu
 *
 */
import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;

public class Response {

	String respString = null;
	StatusLine respStatus = null;
	Integer respCode = null;

	public Response() {
		// TODO Auto-generated constructor stub
	}

	public void setResponse(CloseableHttpResponse httpResponse) throws ParseException, IOException {
		// TODO Auto-generated method stub
		HttpEntity entity = httpResponse.getEntity();
		respString = EntityUtils.toString(entity);
	}

	public String getResponse() {
		return respString;
	}
}
