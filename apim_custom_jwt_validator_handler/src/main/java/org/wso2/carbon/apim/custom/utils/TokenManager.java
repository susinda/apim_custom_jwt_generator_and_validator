package org.wso2.carbon.apim.custom.utils;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.synapse.MessageContext;
import org.apache.synapse.core.axis2.Axis2MessageContext;
import org.wso2.carbon.apim.custom.dto.Token;

import sun.misc.BASE64Encoder;


public class TokenManager {
	
	private static final Log log = LogFactory.getLog(TokenManager.class);
    private static String securityHeader = HttpHeaders.AUTHORIZATION;
    private static String consumerKeyHeaderSegment = "Bearer";
    private static String oauthHeaderSplitter = ",";
    private static String consumerKeySegmentDelimiter = " ";
    private static final String X_JWT_ASSERTION = "X-JWT-Assertion";
	
    private HTTPClient httpClient;    

    public TokenManager() {
        httpClient = new HTTPClient();
    }

    public Token getToken(String username, String password, String consumerKey, String consumerSecret, String submitUrl){
       
        String contentType = "application/x-www-form-urlencoded";
       
        log.info("Invoking getToken() method ");
        
        try {
            String applicationToken = consumerKey + ":" + consumerSecret;
            BASE64Encoder base64Encoder = new BASE64Encoder();
            applicationToken = "Basic " + base64Encoder.encode(applicationToken.getBytes()).trim();

            String payload = "grant_type=password&username="+username+"&password="+password;
            
            HttpResponse httpResponse = httpClient.doPost(submitUrl, applicationToken, payload, contentType);
            
            if (httpResponse.getStatusLine().getStatusCode() != 200) {
            	log.info("\nToken getToken() Response FAILED, status code " + httpResponse.getStatusLine().getStatusCode());
            	return null;
            }
            String response = httpClient.getResponsePayload(httpResponse);
            
            return JSONUtil.getAccessToken(response);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static String extractCustomerKeyFromAuthHeader(MessageContext messageContext) {
    	
    	Map transportHeaders = (Map) ((Axis2MessageContext) messageContext).getAxis2MessageContext().
                getProperty(org.apache.axis2.context.MessageContext.TRANSPORT_HEADERS);

        String authHeader = (String) transportHeaders.get(securityHeader);
        if (authHeader == null) {
            return null;
        }

        if (authHeader.startsWith("OAuth ") || authHeader.startsWith("oauth ")) {
            authHeader = authHeader.substring(authHeader.indexOf("o"));
        }

        String[] headers = authHeader.split(oauthHeaderSplitter);
        if (headers != null) {
            for (int i = 0; i < headers.length; i++) {
                String[] elements = headers[i].split(consumerKeySegmentDelimiter);
                if (elements != null && elements.length > 1) {
                    int j = 0;
                    boolean isConsumerKeyHeaderAvailable = false;
                    for (String element : elements) {
                        if (!"".equals(element.trim())) {
                            if (consumerKeyHeaderSegment.equals(elements[j].trim())) {
                                isConsumerKeyHeaderAvailable = true;
                            } else if (isConsumerKeyHeaderAvailable) {
                                return removeLeadingAndTrailing(elements[j].trim());
                            }
                        }
                        j++;
                    }
                }
            }
        }
        return null;
    }
	
	 private static String removeLeadingAndTrailing(String base) {
	        String result = base;

	        if (base.startsWith("\"") || base.endsWith("\"")) {
	            result = base.replace("\"", "");
	        }
	        return result.trim();
	 }

	public static String extractJWTToken(MessageContext messageContext) {
		
		Map transportHeaders = (Map) ((Axis2MessageContext) messageContext).getAxis2MessageContext().
                getProperty(org.apache.axis2.context.MessageContext.TRANSPORT_HEADERS);
		
		String jwtToken = null;
    	if (transportHeaders != null && transportHeaders.containsKey(X_JWT_ASSERTION)) {
    		jwtToken = (String) transportHeaders.get(X_JWT_ASSERTION);
    	}

		return jwtToken;
	}
	
}
