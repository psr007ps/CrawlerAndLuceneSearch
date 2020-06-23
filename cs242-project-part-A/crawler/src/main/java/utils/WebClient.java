/**
 * 
 */
package utils;

/**
 * @author sattu
 *
 */
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import org.apache.http.conn.ConnectTimeoutException;

import org.apache.http.entity.StringEntity;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;

import config.WebRequestConfig;
import response.Response;

public class WebClient {

	CloseableHttpClient httpclient = null;
	CloseableHttpResponse httpResponse = null;

	public WebClient() {
	}

	private CloseableHttpClient createHttpClient(WebRequestConfig wReqConfig)
			throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {

		CloseableHttpClient httpclient = HttpClients.createDefault();
		return httpclient;
	}

	public Response doGET(WebRequestConfig wReqConfig) {
		try {
			HttpGet httpGet = new HttpGet(wReqConfig.getURL());
			for (Map.Entry<String, String> entry : wReqConfig.getHeader().entrySet()) {
				httpGet.addHeader(entry.getKey(), entry.getValue());
			}

			Response response = new Response();
			httpclient = createHttpClient(wReqConfig);
			httpResponse = httpclient.execute(httpGet);
			response.setResponse(httpResponse);
			httpResponse.close();
			httpGet.releaseConnection();
			httpclient.close();
			return response;
		} catch (Exception e) {
			System.out.println(":::: ERROR : Error occurred in doGet() ::::");
			e.printStackTrace();
			return null;
		}
	}

	public Response doPost(WebRequestConfig wReqConfig) throws InterruptedException, KeyManagementException,
			NoSuchAlgorithmException, KeyStoreException, ClientProtocolException, IOException {
		try {
			HttpPost httpPost = new HttpPost(wReqConfig.getURL());
			for (Map.Entry<String, String> entry : wReqConfig.getHeader().entrySet()) {
				httpPost.addHeader(entry.getKey(), entry.getValue());
			}
			httpPost.setEntity(new StringEntity(wReqConfig.getBody()));

			Response response = new Response();
			httpclient = createHttpClient(wReqConfig);
			httpResponse = httpclient.execute(httpPost);
			response.setResponse(httpResponse);
			httpResponse.close();
			httpPost.releaseConnection();
			httpclient.close();
			return response;
		} catch (ConnectTimeoutException | SocketTimeoutException e) {
			System.out.println(":::: ERROR : Error occurred in doPost() ::::");
			e.printStackTrace();
			return null;
		}
	}

	public void close() {
		try {
			httpclient.close();
			httpResponse.close();
		} catch (Exception e) {

		} finally {
			httpclient = null;
			httpResponse = null;
		}
	}

}