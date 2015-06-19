package org.wso2.carbon.apim.custom;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.synapse.MessageContext;
import org.apache.synapse.rest.AbstractHandler;
import org.wso2.carbon.apim.custom.utils.JSONUtil;
import org.wso2.carbon.apim.custom.utils.JWTUtil;
import org.wso2.carbon.apim.custom.utils.JwtProcessor;
import org.wso2.carbon.apim.custom.utils.TokenManager;
import org.wso2.carbon.apim.custom.dto.JWTAssersion;
import org.wso2.carbon.apimgt.gateway.handlers.security.APIAuthenticationHandler;


public class JwtValidationHandler extends AbstractHandler {


    private static final Log log = LogFactory.getLog(JwtValidationHandler.class);
    private static APIAuthenticationHandler defaultHandler = new APIAuthenticationHandler();
    
    
    //...............properties read from handler configuration .............................................
	private String keystorePath = "/home/susinda/products/APIM/wso2am-1.8.0/repository/resources/security/wso2carbon.jks";; 
    private String keystorePassword = "wso2carbon";
    private String keyAlias = "wso2carbon";
    private String submitUrl = "http://localhost:8280/token";
    private String consumerKey = "uwhnNnlVNKvguRVfY35c2815ZMIa";
    private String consumerSecret = "6gRmE2BnNWf1fA2XtEGG_LD9nFsa";
    private String password = "admin";
    
    
    public String getKeystorePath() {
		return keystorePath;
	}

	public void setKeystorePath(String keystorePath) {
		this.keystorePath = keystorePath;
	}

	public String getKeystorePassword() {
		return keystorePassword;
	}

	public void setKeystorePassword(String keystorePassword) {
		this.keystorePassword = keystorePassword;
	}

	public String getKeyAlias() {
		return keyAlias;
	}

	public void setKeyAlias(String keyAlias) {
		this.keyAlias = keyAlias;
	}

	public String getSubmitUrl() {
		return submitUrl;
	}

	public void setSubmitUrl(String submitUrl) {
		this.submitUrl = submitUrl;
	}

	public String getConsumerKey() {
		return consumerKey;
	}

	public void setConsumerKey(String consumerKey) {
		this.consumerKey = consumerKey;
	}

	public String getConsumerSecret() {
		return consumerSecret;
	}

	public void setConsumerSecret(String consumerSecret) {
		this.consumerSecret = consumerSecret;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	//...............properties read from handler configuration .........................

   
   
    public boolean handleRequest(MessageContext messageContext) {
    	
    	log.info("================TEST HANDLER handleRequest ===========================");

        String apiKey = TokenManager.extractCustomerKeyFromAuthHeader(messageContext);
        log.debug(" apiKey = " + apiKey);

        String jwtToken = TokenManager.extractJWTToken(messageContext);
        log.debug(" jwtToken = " + jwtToken);
      
    	
    	if (jwtToken != null && !jwtToken.isEmpty()) {        
    		
    		log.info("jwt header is present therefore further validating jwt signature ");
    		JwtProcessor processor = new JwtProcessor(jwtToken);
        	
    		JWTAssersion jwt = JSONUtil.getAssersion(processor.getDecodedAssersion(processor.getAssersion()));
        	String endUser = jwt.getSubscriber();
        	String grantType = jwt.getGrantType();
        	String endUserConsumerKey = jwt.getConsumerKey();
        	String endUserToken = jwt.getEnduserToken();
        	log.debug(" grantType=" +  grantType + "  endUser=" + endUser + "    endUserConsumerKey=" +   endUserConsumerKey   + "    endUserToken=" + endUserToken );
        	
	    	boolean signatureValid = JWTUtil.validateJWTSignature(jwtToken, keystorePath, keystorePassword, keyAlias);
	    	log.info("signatureValid="+ signatureValid); 
	    	
	    	if (!signatureValid) {
	    		return false;
	    	}
    	} 

	    defaultHandler.init(messageContext.getEnvironment());
	    return defaultHandler.handleRequest(messageContext);
    }

    public boolean handleResponse(MessageContext messageContext) {
    	log.info("======================CUSTOM HANDLER handleResponse =======================");
        return defaultHandler.handleResponse(messageContext);
    }

}