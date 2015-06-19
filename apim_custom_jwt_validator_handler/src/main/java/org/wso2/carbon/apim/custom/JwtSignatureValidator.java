package org.wso2.carbon.apim.custom;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;

import org.apache.axiom.util.base64.Base64Utils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class JwtSignatureValidator {

	String signAlgorithm;
	Certificate certificate;
	
	public void setSignAlgorithm(String signAlgorithm) {
		this.signAlgorithm = signAlgorithm;
	}
	
	public void setCertificate(Certificate certificate) {
		this.certificate = certificate;
	}
	
	public JwtSignatureValidator() {
		
	}
	
	private static final Log log = LogFactory.getLog(JwtSignatureValidator.class);
	
	//Validates jwt signature against the certificate
    public JwtSignatureValidator(String signAlgorithm, Certificate certificate) {
    	this.signAlgorithm = signAlgorithm;
    	this.certificate = certificate;
	}
	
	public boolean validateSignature(String assersion, String signature) throws KeyStoreException,
		NoSuchAlgorithmException, CertificateException, IOException,
		InvalidKeyException, SignatureException {
				
		PublicKey publicKey = certificate.getPublicKey();
		Signature signatureInstance = Signature.getInstance(signAlgorithm);
		signatureInstance.initVerify(publicKey);
		signatureInstance.update(assersion.getBytes());
		byte[] decodedBytes = Base64Utils.decode(signature);
		
		boolean isValid = signatureInstance.verify(decodedBytes);
		return isValid;
	}
	
}
