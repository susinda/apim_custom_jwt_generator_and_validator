package org.wso2.carbon.apim.custom.jwt;

import org.wso2.carbon.apimgt.impl.dto.APIKeyValidationInfoDTO;
import org.wso2.carbon.apimgt.impl.token.JWTGenerator;
import org.wso2.carbon.apimgt.api.*;

import java.util.HashMap;
import java.util.Map;

// based on http://wso2.com/library/articles/2014/12/customize-json-web-token-generation-with-wso2-api-manager-1.8.0/
public class ChainGrantTypeSupportedTokenGeneraor extends JWTGenerator {

    public Map populateCustomClaims(APIKeyValidationInfoDTO keyValidationInfoDTO, String apiContext, String version, String accessToken)
            throws APIManagementException {
        Map map = new HashMap();
        map.put("consumer_key", keyValidationInfoDTO.getConsumerKey());
        map.put("end_user_token" , accessToken);
        map.put("grant_type" , "http://oauth.net/grant_type/chain");
        return map;
    }
}