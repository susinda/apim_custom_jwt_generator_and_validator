package org.wso2.carbon.apim.custom.dto;

public class JWTAssersion {
	
	private String issuer;
	private int expiry;
	private String subscriber;
	private String applicationId;
	private String applicationName;
	private String applicationTier;
	private String apiContext;
	private String version;
	private String tier;
	private String keyType;
	private String userType;
	private String enduser;
	private String enduserTenantId;
	private String grantType;
	private String consumerKey;
	private String enduserToken;
	
	
	public String getIssuer() {
		return issuer;
	}
	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}
	
	public int getExpiry() {
		return expiry;
	}
	public void setExpiry(int expiry) {
		this.expiry = expiry;
	}
	
	public String getSubscriber() {
		return subscriber;
	}
	public void setSubscriber(String subscriber) {
		this.subscriber = subscriber;
	}
	
	public String getApplicationId() {
		return applicationId;
	}
	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}
	
	public String getApplicationName() {
		return applicationName;
	}
	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}
	
	public String getApplicationTier() {
		return applicationTier;
	}
	public void setApplicationTier(String applicationTier) {
		this.applicationTier = applicationTier;
	}
	
	public String getApiContext() {
		return apiContext;
	}
	public void setApiContext(String apiContext) {
		this.apiContext = apiContext;
	}
	
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	
	public String getTier() {
		return tier;
	}
	public void setTier(String tier) {
		this.tier = tier;
	}
	
	public String getKeyType() {
		return keyType;
	}
	public void setKeyType(String keyType) {
		this.keyType = keyType;
	}
	
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	
	public String getEnduser() {
		return enduser;
	}
	public void setEnduser(String enduser) {
		this.enduser = enduser;
	}
	
	public String getEnduserTenantId() {
		return enduserTenantId;
	}
	public void setEnduserTenantId(String enduserTenantId) {
		this.enduserTenantId = enduserTenantId;
	}
	
	public String getGrantType() {
		return grantType;
	}
	public void setGrantType(String grantType) {
		this.grantType = grantType;
	}
	
	public String getConsumerKey() {
		return consumerKey;
	}
	public void setConsumerKey(String consumerKey) {
		this.consumerKey = consumerKey;
	}
	
	public String getEnduserToken() {
		return enduserToken;
	}
	public void setEnduserToken(String enduserToken) {
		this.enduserToken = enduserToken;
	}

	
//	"iss": "wso2.org/products/am",
//    "exp": 1432896339345,
//    "http://wso2.org/claims/subscriber": "admin",
//    "http://wso2.org/claims/applicationid": "1",
//    "http://wso2.org/claims/applicationname": "DefaultApplication",
//    "http://wso2.org/claims/applicationtier": "Unlimited",
//    "http://wso2.org/claims/apicontext": "/apimapi",
//    "http://wso2.org/claims/version": "1.0.0",
//    "http://wso2.org/claims/tier": "Unlimited",
//    "http://wso2.org/claims/keytype": "PRODUCTION",
//    "http://wso2.org/claims/usertype": "APPLICATION",
//    "http://wso2.org/claims/enduser": "admin@carbon.super",
//    "http://wso2.org/claims/enduserTenantId": "-1234",
//    "grant_type": "http://oauth.net/grant_type/chain",
//    "consumer_key": "08iiAILYXDC87K9MUyXCziDBdGEa",
//    "end_user_token": "af86a16c6925e1bda258695586c517a0"

}

