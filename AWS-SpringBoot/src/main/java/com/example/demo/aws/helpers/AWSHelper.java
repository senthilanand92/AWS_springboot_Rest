package com.example.demo.aws.helpers;

import org.springframework.stereotype.Component;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.BasicAWSCredentials;
import com.example.demo.aws.constants.AWSConstants;

@Component
public class AWSHelper implements AWSConstants {
	
	 public BasicAWSCredentials getBasicAWSCredtials(){
    	 BasicAWSCredentials basicCred = new BasicAWSCredentials(AWS_ACCESS_KEY, AWS_SECRET_KEY);
    	return basicCred;
    }

  public ClientConfiguration getClientConfig() {

	    ClientConfiguration clientCfg = new ClientConfiguration();
	    clientCfg.setProtocol(Protocol.HTTP);
	    clientCfg.setProxyHost("10.10.103.5");
	    clientCfg.setProxyPort(8080);

	    clientCfg.setUseExpectContinue(false);
	    return clientCfg;
  }

}
