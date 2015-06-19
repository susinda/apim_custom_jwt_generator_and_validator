package org.wso2.carbon.apim.custom.utils;

import org.apache.axiom.util.base64.Base64Utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

public class JwtProcessor {

	String signAlgorithm;
	String assersion;
	String signature;
 
	public String getSignAlgorithm() {
		return signAlgorithm;
	}
	
	public String getAssersion() {
		return assersion;
	}
	
	public String getSignature() {
		return signature;
	}

	//gets jwt token and seperate into parts
	public JwtProcessor (String jwtToken) {
		 
		if (jwtToken != null) {
	    	String[] jwtParts = jwtToken.split("\\.");
    		if (jwtParts.length == 3) {
    			signAlgorithm = jwtParts[0];
    			signAlgorithm = getAlgorithmFormJWT(signAlgorithm);
    			assersion = jwtParts[0] + "." + jwtParts[1]; //add with a dot;
    	    	signature = jwtParts[2];
    		}
    	}
	}
	
	private String getAlgorithmFormJWT(String algorithm) throws JsonParseException {
		JsonParser parser = new JsonParser();
		byte[] decoded = Base64Utils.decode(algorithm);
		String jwtHeader = new String(decoded, java.nio.charset.Charset.forName("UTF-8"));
		JsonElement header = parser.parse(jwtHeader);
		algorithm = header.getAsJsonObject().get("alg").getAsString();
		return algorithm;
	}
	
	public String getDecodedAssersion(String assersion) throws JsonParseException {
		String[] jwtParts = assersion.split("\\.");
		byte[] decoded = Base64Utils.decode(jwtParts[1]);
		String jwtBody = new String(decoded, java.nio.charset.Charset.forName("UTF-8"));
		return jwtBody;
	}
	 
	
}
