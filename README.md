# WSO2 API Manager - Custom Jwt generator and validator
This is a RND project to test the trusted comminication between two api manager servers. 
Purpose is to check the api calls to apim2 is comes from apim1.
To achive this, jwt signature validation mechanism is used.

The project 'apim_custom_jwt_generator' generates jwt token with some additonal info, and apim_custom_jwt_validator_handler project is a custom handler which does the jwt validation based on the jwt signature and the additional info provided in above jwt token.


Test

Note that this is tried with APIM 1.8.0 only

Build the apim_custom_jwt_generator project and add the oauth2-chaingranttype-jwt-1.0.0.jar file to &lt;APIM1&gt;/repository/components/lib/ directory of the product. Then add the following element to specify that our custom implementation should be used as the JWTGenerator. This should come under the APIConsumerAuthentication section in api-manager.xml. Also, JWT generation should be enabled at this point.

&lt;APIConsumerAuthentication&gt;
....
&lt;TokenGeneratorImpl&gt;org.wso2.carbon.test.CustomTokenGenerator&lt;/TokenGeneratorImpl&gt;
....
&lt;/APIConsumerAuthentication&gt;



Build the apim_custom_jwt_validator_handler project and copy the JAR file to &lt;APIM_HOME&gt;/repository/components/lib folder.
Navigate to the folder&lt;APIM2&gt;/repository/deployment/server/synapse-configs/default/api.
Locate the api file in which you want to add the custom handler and add it as follows.

Replce the following line 
&lt;handler class="org.wso2.carbon.apimgt.gateway.handlers.security.APIAuthenticationHandler"/&gt; 

with 
&lt;handler class="org.wso2.carbon.apim.custom.JwtValidationHandler"&gt;
        &lt;property name="keystorePath" value="/home/apim/keystores/wso2carbon.jks"/&gt;
        &lt;property name="keystorePassword" value="wso2carbon"/&gt;
	&lt;property name="keyAlias" value="wso2carbon"/&gt;
&lt;/handler&gt;

Not ethat you have to have the private key of apim1 in the keystore of apim2.
Replace the keystorePath, keystorePassword keyAlias values with your values. Given here are sample values only.

Call a api in apim2 via apim1. And see the logs. If you change the certificate of apim1 you will see that authentication failure for api calls.







