/**
 * 
 */
package org.microworld.client;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;
import org.microworld.logging.Log;
import org.microworld.utils.StreamConverter;

/**
 * @author riccardo HEAD Asks for the response identical to the one that would
 *         correspond to a GET request, but without the response body. This is
 *         useful for retrieving meta-information written in response headers,
 *         without having to transport the entire content. GET Requests a
 *         representation of the specified resource. Note that GET should not be
 *         used for operations that cause side-effects, such as using it for
 *         taking actions in web applications. One reason for this is that GET
 *         may be used arbitrarily by robots or crawlers, which should not need
 *         to consider the side effects that a request should cause. See safe
 *         methods below. POST Submits data to be processed (e.g., from an HTML
 *         form) to the identified resource. The data is included in the body of
 *         the request. This may result in the creation of a new resource or the
 *         updates of existing resources or both. PUT Uploads a representation
 *         of the specified resource. DELETE Deletes the specified resource.
 */
public abstract class DycapoHttpClient {
	public static final String URL_BASIS = "http://test.dycapo.org/api/";
	private static UsernamePasswordCredentials USRN_PWD_CRD = null;

	/**
	 * @param code
	 * @return
	 * @throws DycapoException
	 */

	public static final String MESSAGE = "message";
	public static final int HEAD = 0;
	public static final int GET = 1;
	public static final int POST = 2;
	public static final int PUT = 3;
	public static final int DELETE = 4;

	public static final String callDycapo(int method, String uri,
			JSONObject jsonObject, String username, String password) {

		HttpResponse response = doJSONRequest(method, uri, jsonObject,
				username, password);

		try {

			String stringResp = StreamConverter.convertStreamToString(response
					.getEntity().getContent());
			return stringResp;
		} catch (IllegalStateException e) {
			e.printStackTrace();

		} catch (IOException e) {
			e.printStackTrace();

		} catch (NullPointerException e) {
			e.printStackTrace();

		} finally {
			USRN_PWD_CRD = null;
		}
		return null;
	}

	public static final String uriBuilder(String puri) {
		StringBuilder sb = new StringBuilder();
		sb.append(URL_BASIS);
		sb.append(puri);
		sb.append("/");
		return sb.toString();
	}

	private static final HttpResponse doJSONRequest(int method, String uri,
			JSONObject jsonObject, String username, String password) {
		DefaultHttpClient httpclient = new DefaultHttpClient();

		URI uriF;
		try {
			uriF = new URI(uri);

			if (username instanceof String && password instanceof String) {
				USRN_PWD_CRD = new UsernamePasswordCredentials(username,
						password);
				httpclient.getCredentialsProvider().setCredentials(
						new AuthScope(uriF.getHost(), uriF.getPort(),
								AuthScope.ANY_REALM), USRN_PWD_CRD);
				Log.verbose("DycapoHttpClient", USRN_PWD_CRD.toString());
			}
			HttpResponse response = null;
			StringEntity se;
			switch (method) {
			case HEAD:
				HttpHead headRequest = new HttpHead(uriF);
				response = httpclient.execute(headRequest);
				break;
			case GET:
				HttpGet getRequest = new HttpGet(uriF);
				response = httpclient.execute(getRequest);
				break;

			case POST:
				se = new StringEntity(jsonObject.toString());

				HttpPost requestPost = new HttpPost(uriF);
				requestPost.setHeader("Accept", "application/json");
				requestPost.setHeader("Content-type", "application/json");
				requestPost.setEntity(se);
				response = httpclient.execute(requestPost);
				break;

			case PUT:
				se = new StringEntity(jsonObject.toString());

				HttpPut requestPut = new HttpPut(uriF);
				requestPut.setHeader("Accept", "application/json");
				requestPut.setHeader("Content-type", "application/json");
				requestPut.setEntity(se);
				response = httpclient.execute(requestPut);
				break;
			case DELETE:
				HttpDelete requestDelete = new HttpDelete(uriF);
				response = httpclient.execute(requestDelete);
				break;
			default:

			}

			return response;
		} catch (ClientProtocolException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		} catch (URISyntaxException e1) {

			e1.printStackTrace();

		}

		return null;
	}
}
