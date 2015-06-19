package org.wso2.carbon.apim.custom.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.apim.custom.JwtSignatureValidator;

public class JWTUtil {
	
	private static final Log log = LogFactory.getLog(JWTUtil.class);
	    

	public static boolean validateJWTSignature(String jwtToken, String keystorePath, String keystorePassword, String keyAlias)	{
		
		JwtProcessor processor = new JwtProcessor(jwtToken);
		String signAlgorithm = processor.getSignAlgorithm();
		String apimAssersion = processor.getAssersion();
    	String apimSignedJWT = processor.getSignature();

    	//If JWT configs are wrong return false
    	if (StringUtils.isEmpty(signAlgorithm) || StringUtils.isEmpty(apimAssersion) || StringUtils.isEmpty(apimSignedJWT)) {
    		log.error("Invalid JWT token");
    		return false;
    	}
    	
    	Boolean isValid = false;
        try {
        	
    	    Certificate cert = getCertificateFromKeystore(keystorePath, keystorePassword, keyAlias);
    	    
    	    JwtSignatureValidator validator = new JwtSignatureValidator(signAlgorithm, cert);
    	    
    	    isValid = validator.validateSignature(apimAssersion, apimSignedJWT);
			if (log.isDebugEnabled()) {
				log.debug("APIM Signature " +  isValid);
			} 
			if (!isValid) {
				log.error("APIM Signature Validation Failed");
			}
			
		} catch (Exception e) {
			log.error("Error occured while validating signature "+ e.getMessage());
		}
        
        return isValid;
	}

	
	private static Certificate getCertificateFromKeystore(String keystorePath, String keystorePassword, String keyAlias)
			throws KeyStoreException, IOException, NoSuchAlgorithmException,
			CertificateException {
		
		InputStream file = new FileInputStream(keystorePath); 
		KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
		keystore.load(file, keystorePassword.toCharArray());
		Certificate cert = keystore.getCertificate(keyAlias);
		file.close();
		return cert;
	}
}
