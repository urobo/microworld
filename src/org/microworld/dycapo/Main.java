/**
 * 
 */
package org.microworld.dycapo;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.microworld.utils.StreamConverter;

/**
 * @author riccardo
 *
 */
public class Main {
	public static final String USERNAME ="ozzy";
	public static final String PASSWORD ="password";
	public static final String URL = "http://test.dycapo.org/api/persons/";
	
	public static void main (String[] args) {
		
		URI uri;
		try {
			uri = new URI(URL);
		
			DefaultHttpClient httpclient = new DefaultHttpClient();
			httpclient.getCredentialsProvider().setCredentials(
					new AuthScope(uri.getHost(), uri.getPort(),
							AuthScope.ANY_REALM), new UsernamePasswordCredentials(USERNAME,
									PASSWORD));
			HttpGet getRequest = new HttpGet(uri);
			HttpResponse response = (HttpResponse) httpclient.execute(getRequest);
			StreamConverter.convertStreamToString(response.getEntity().getContent());
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
