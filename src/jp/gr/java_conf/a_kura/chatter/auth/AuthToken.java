package jp.gr.java_conf.a_kura.chatter.auth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthToken {

    private String accessToken;
	private String id;
    private String issuedAt;
    private String scope;
    private String refreshToken;
    private String instanceUrl;
    private String signature;
    private String tokenType;

    public AuthToken() {
    	
    }

    public AuthToken(String accessToken) {
        this.accessToken = accessToken;
    }
    public String getAccessToken() {
		return accessToken;
	}

	public String getId() {
		return id;
	}

	public String getIssuedAt() {
		return issuedAt;
	}

	public String getScope() {
		return scope;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public String getInstanceUrl() {
		return instanceUrl;
	}

	public String getSignature() {
		return signature;
	}

	public String getTokenType() {
		return tokenType;
	}
}