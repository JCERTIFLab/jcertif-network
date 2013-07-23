
package com.jcertif.reseaujcertif.services;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnManagerPNames;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.entity.AbstractHttpEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;

public class MyHttpConnectionManager {

	private static DefaultHttpClient 	client;

	public static MyHttpConnection getConnectionForSoapRequest(
			String uri, 
			String request) 
	throws UnsupportedEncodingException{
		if(client == null){
			initClient();
		}
		MyHttpConnection connection = new MyHttpConnection();
		connection.mURI = uri;
		connection.mEntity = new StringEntity(request);
		connection.mEntity.setContentEncoding("UTF-8");
		connection.setHeader("Content-Type", "text/xml; charset=utf-8");
		return connection;
	}

	public static MyHttpConnection getConnectionForEmptyRequest(String uri){
		if(client == null){
			initClient();
		}
		MyHttpConnection connection = new MyHttpConnection();
		connection.mURI = uri;
		return connection;
	}

	private static void initClient(){
		SchemeRegistry schemeRegistry = new SchemeRegistry();
		schemeRegistry.register(
				new Scheme(
						"http", 
						PlainSocketFactory.getSocketFactory(), 
						80));

		BasicHttpParams params = new BasicHttpParams();
		params.setParameter(
				ConnManagerPNames.MAX_TOTAL_CONNECTIONS, 
				1);
		params.setParameter(
				ConnManagerPNames.MAX_CONNECTIONS_PER_ROUTE, 
				new ConnPerRouteBean(1));
		params.setParameter(
				HttpProtocolParams.USE_EXPECT_CONTINUE, 
				false);

		HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);

		ThreadSafeClientConnManager clientConnectionManager = 
			new ThreadSafeClientConnManager(params, schemeRegistry);
		client = new DefaultHttpClient(clientConnectionManager, params);
		setDefaultRetryHandler();
	}

	private static void setDefaultRetryHandler(){
		HttpRequestRetryHandler retryHandler = 
			new HttpRequestRetryHandler(){
			
			public boolean retryRequest(
					IOException exception, 
					int executionCount,
					HttpContext context) {
				if (executionCount >= 5) {
					//Si plus de 5 essais, pas de nouvelle tentative
					return false;
				}
				if (exception instanceof NoHttpResponseException) {
					//Si pas de réponse, on retente
					return true;
				}
				if (exception instanceof IOException) {
					//On retente si la poignée de main SSL a echoué
					return true;
				}
				HttpRequest request = (HttpRequest)context.getAttribute(
						ExecutionContext.HTTP_REQUEST);
				boolean idempotent = 
					!(request instanceof HttpEntityEnclosingRequest); 
				if (idempotent) {
					//On ré-essaye si la requête est considérée idempotente
					return true;
				}
				return false;
			}

		};

		client.setHttpRequestRetryHandler(retryHandler);
	}

	public static void setRetryHandler(HttpRequestRetryHandler handler){
		client.setHttpRequestRetryHandler(handler);
	}

	/********************************************************/
	/***												  ***/
	/***				Classe de connexion				  ***/
	/***												  ***/
	/********************************************************/

	public static class MyHttpConnection{

		private 	String						mURI;
		protected 	AbstractHttpEntity			mEntity;
		private 	HashMap<String, String>		mHttpHeaders;

		private	 	HttpPost 					mPostRequest;

		private MyHttpConnection(){
			mHttpHeaders = new HashMap<String, String>();
		}

		public void setHeader(String key, String value){
			mHttpHeaders.remove(key);
			mHttpHeaders.put(key, value);
		}

		public InputStream execute() throws NullPointerException{
			
			//Initialisation de la requête
			mPostRequest = new HttpPost(mURI);

			//Ajout des headers personnalisés
			Iterator<Entry<String, String>> iterator = mHttpHeaders.entrySet().iterator();
			while(iterator.hasNext()){
				Entry<String, String> entry = iterator.next();
				mPostRequest.setHeader(entry.getKey(), entry.getValue());
			}

			HttpResponse httpResponse;

			//Envoi de la requête
			boolean retry = false;
			try {		
				mPostRequest.setEntity(mEntity);
				httpResponse = client.execute(mPostRequest);
				return httpResponse.getEntity().getContent();
			} catch (SocketException e) {
				retry = true;
			} catch (IOException e) {
				retry = true;
			}

			if(retry){
				try {
					httpResponse = client.execute(mPostRequest);
					return httpResponse.getEntity().getContent();
				} catch (ClientProtocolException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}

			throw new NullPointerException("Le flux de données renvoyé est nul");
		}

	}


}
