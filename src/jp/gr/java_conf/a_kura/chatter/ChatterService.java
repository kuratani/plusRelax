package jp.gr.java_conf.a_kura.chatter;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

import jp.gr.java_conf.a_kura.chatter.attachment.Attachment;
import jp.gr.java_conf.a_kura.chatter.attachment.ExistingContentAttachment;
import jp.gr.java_conf.a_kura.chatter.attachment.LinkAttachment;
import jp.gr.java_conf.a_kura.chatter.attachment.PollAttachment;
import jp.gr.java_conf.a_kura.chatter.auth.AuthToken;
import jp.gr.java_conf.a_kura.chatter.auth.UnauthenticatedException;
import jp.gr.java_conf.a_kura.chatter.message.HashTagSegment;
import jp.gr.java_conf.a_kura.chatter.message.LinkSegment;
import jp.gr.java_conf.a_kura.chatter.message.MentionSegment;
import jp.gr.java_conf.a_kura.chatter.message.Message;
import jp.gr.java_conf.a_kura.chatter.message.TextSegment;

public class ChatterService {
	public final static String FEED_ITEMS = "/services/data/v30.0/chatter/feeds/user-profile/me/feed-items";

	private String clientId = null;
	private String clientSecret = null;
	private String serverUrl = "https://login.salesforce.com";
	private String oauthUrl = "/services/oauth2/token";

	private AuthToken oauthToken = null;

	public ChatterService(String clientId, String clientSecret) {
		this.clientId = clientId;
		this.clientSecret = clientSecret;
	}

	public boolean authenticate(String username, String password) throws IOException, UnauthenticatedException {
		HttpClient httpClient = HttpClientBuilder.create().build();

		HttpPost method = new HttpPost(serverUrl + oauthUrl);
		List<NameValuePair> requestParams = new ArrayList<NameValuePair>();
		requestParams.add(new BasicNameValuePair("grant_type", "password"));
		requestParams.add(new BasicNameValuePair("client_id", URLEncoder.encode(clientId, "UTF-8")));
		requestParams.add(new BasicNameValuePair("client_secret", clientSecret));
		requestParams.add(new BasicNameValuePair("username", username));
		requestParams.add(new BasicNameValuePair("password", password));
		method.setEntity(new UrlEncodedFormEntity(requestParams));

		HttpResponse response = httpClient.execute(method);  
		int responseStatus = response.getStatusLine().getStatusCode();
		String body = EntityUtils.toString(response.getEntity(), "UTF-8");

		if (responseStatus != 200) {
			throw new UnauthenticatedException(responseStatus + " " + body);
		}

		ObjectMapper mapper = new ObjectMapper();
		mapper.setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);
		oauthToken = mapper.readValue(body, AuthToken.class);

		return true;
	}
	
	public boolean post(Message message) throws ChatterException, ClientProtocolException, IOException, UnauthenticatedException {
		return post(message, null);
	}
	
	public boolean post(Message message, Attachment attachment) throws ChatterException, ClientProtocolException, IOException, UnauthenticatedException {
		if(oauthToken == null) {
			throw new UnauthenticatedException("Not authenticate.");
		}

		ObjectMapper mapper = new ObjectMapper();
		FeedItem req = new FeedItem(message, attachment);
		String json = mapper.writeValueAsString(req);
		
		HttpEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);

		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpPost method = new HttpPost(oauthToken.getInstanceUrl() + FEED_ITEMS);
		method.addHeader("Authorization", "Bearer " + oauthToken.getAccessToken());
		method.addHeader("X-PrettyPrint", "1");
		method.setEntity(entity);

		HttpResponse response = httpClient.execute(method);
		int responseStatus = response.getStatusLine().getStatusCode();
		String body = EntityUtils.toString(response.getEntity(), "UTF-8");

		if (responseStatus != 201) {
			throw new ChatterException(responseStatus + " " + body);
		}
		
		return true;
	}

	public String getClientId() {
		return clientId;
	}
	
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	
	public String getClientSecret() {
		return clientSecret;
	}
	
	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}

	public String getServerUrl() {
		return serverUrl;
	}
	
	public void setServerUrl(String serverUrl) {
		this.serverUrl = serverUrl;
	}

}
