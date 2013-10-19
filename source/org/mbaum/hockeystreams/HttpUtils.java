package org.mbaum.hockeystreams;

import static com.mycila.inject.internal.guava.collect.Lists.newArrayList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.wiztools.restclient.ServiceLocator;
import org.wiztools.restclient.View;
import org.wiztools.restclient.bean.HTTPMethod;
import org.wiztools.restclient.bean.Request;
import org.wiztools.restclient.bean.RequestBean;
import org.wiztools.restclient.bean.RequestExecuter;
import org.wiztools.restclient.bean.Response;

public class HttpUtils
{
	public static String loginWithHttpClient( String username, String password, String apiKey ) throws UnsupportedEncodingException
	{
		HttpClient client = HttpClients.createDefault();
		
		HttpPost request = new HttpPost( "https://api.hockeystreams.com/Login?" );
		request.setHeader( "content-type", "application/x-www-form-urlencoded" );
		
        
        StringEntity entity = new StringEntity( "username=" + username +
			     "&password=" + password +"&key=" + apiKey );
		
		request.setEntity( entity );
		
		BufferedReader reader = null;
		try
		{	
			CloseableHttpResponse response = (CloseableHttpResponse) client.execute( request );
			
			return parseResponse( response );
		}
		catch ( ClientProtocolException e1 )
		{
			e1.printStackTrace();
			return "failed";
		}
		catch ( IOException e1 )
		{
			e1.printStackTrace();
			return "failed";
		}
		finally
		{
			IOUtils.closeQuietly( reader );
			request.releaseConnection();
		}
	}
	
	public static String loginWithRestTool( String username, String password, String apiKey ) 
			throws MalformedURLException
	{
		String url = buildHockeyStreamsApiUrl( username, password, apiKey );
				
		RequestBean request = new RequestBean();
		request.setUrl( new URL( url ) );
		request.setMethod( HTTPMethod.POST );
		
		View view = new View()
		{
			@Override
			public void doCancelled()
			{
				System.out.println( "cancelled" );
			}

			@Override
			public void doEnd()
			{
				System.out.println( "end" );
			}

			@Override
			public void doError( String arg0 )
			{
				System.out.println( "error" );
			}

			@Override
			public void doResponse( Response arg0 )
			{
				System.out.println( "response: " + arg0.getResponseBody() );
			}

			@Override
			public void doStart( Request arg0 )
			{
				System.out.println( "request: " + arg0.getUrl().toExternalForm() );
			}
		};
		
		RequestExecuter executer = ServiceLocator.getInstance(RequestExecuter.class);

		// 'request' and 'view' are the objects created in the previous sections:
		executer.execute( request, view );

		return "failed";
	}
	
	public static String loginWithRest(String username, String password, String apiKey) 
			throws URISyntaxException, ClientProtocolException, IOException
	{
        HttpRequestBase request = new HttpPost();
        
		request.setURI(new URI( "https://api.hockeystreams.com/Login" ) );

        // Attach form entity if necessary. Note: some REST APIs
        // require you to POST JSON. This is easy to do, simply use
        // postRequest.setHeader('Content-Type', 'application/json')
        // and StringEntity instead. Same thing for the PUT case 
        // below.
        HttpPost postRequest = (HttpPost) request;

        UrlEncodedFormEntity formEntity = 
        			new UrlEncodedFormEntity( newArrayList( new BasicNameValuePair( "username", username ),
        								                    new BasicNameValuePair( "password", password ),
        								                    new BasicNameValuePair( "apiKey", apiKey )) );
        postRequest.setEntity(formEntity);
        
        System.out.println( "request: " + postRequest );
        
        HttpClient client = HttpClients.createDefault();
        
        HttpResponse response = client.execute( postRequest );
        
        return parseResponse( response );
	}
	
	public static String loginWithUrlConnection(String username, String password, String apiKey) throws IOException
	{
		URL url = new URL(buildHockeyStreamsApiUrl( username, password, apiKey )); 
		HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
		connection.setDoInput(true); 
		connection.setDoOutput(true);
		connection.setRequestMethod("POST");
		
		OutputStream requestBody = connection.getOutputStream();
		
		requestBody.flush();
		
		return parseResponse( connection.getInputStream() );
	}

	private static void setRequestEntity( HttpPost request )
			throws UnsupportedEncodingException
	{
//		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
//		nameValuePairs.add( new BasicNameValuePair( "status", "" ) );
//		nameValuePairs.add( new BasicNameValuePair( "uid", "" ) );
//		nameValuePairs.add( new BasicNameValuePair( "username", "" ) );
//		nameValuePairs.add( new BasicNameValuePair( "favteam", "" ) );
//		nameValuePairs.add( new BasicNameValuePair( "membership", "" ) );
//		nameValuePairs.add( new BasicNameValuePair( "token", "" ) );
//		request.setEntity( new UrlEncodedFormEntity( nameValuePairs ) );
		request.setEntity( new StringEntity( "product" ) );
	}

	private static String buildHockeyStreamsApiUrl( String username,
			String password, String apiKey )
	{
		String uri = "https://api.hockeystreams.com/Login?username=" + username +
				     "&password=" + password +"&key=" + apiKey;
		return uri;
	}
	
	private static String parseResponse( HttpResponse response ) throws UnsupportedEncodingException, IllegalStateException, IOException
	{
		InputStream responseStream = response.getEntity().getContent();
		return parseResponse( responseStream );
	}

	private static String parseResponse( InputStream responseStream )
			throws UnsupportedEncodingException, IOException
	{
		BufferedReader reader = null;
		try
		{			
			reader = new BufferedReader( new InputStreamReader(responseStream, "UTF-8") );
			StringBuilder builder = new StringBuilder();
			
			String line = "";
			while ((line = reader.readLine()) != null) {
				builder.append(line).append("\n");
			}
			return builder.toString();
		}
		finally
		{
			IOUtils.closeQuietly( reader );
		}
	}
}
