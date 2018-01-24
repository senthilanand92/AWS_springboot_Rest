package com.example.demo.aws.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.example.demo.aws.helpers.AWSHelper;

@Component
public class AWSClientFactory {
	
	@Autowired
    AWSHelper helperClass;
	
	public AWSHelper getHelperClass() {
		return helperClass;
	}
	public void setHelperClass(AWSHelper helperClass) {
		this.helperClass = helperClass;
	}
	public  AmazonDynamoDB client;
	
	public AmazonS3 getS3Client(){
		AmazonS3 s3 = new AmazonS3Client(helperClass.getBasicAWSCredtials(),helperClass.getClientConfig());
		 Region usWest2 = Region.getRegion(Regions.AP_SOUTH_1);
		    s3.setRegion(usWest2);
		return s3;
	}
	public  AmazonDynamoDB initializeClient(){
		if(this.client == null){
			System.out.println("Creating Dynamo DB client");
		this.client  = AmazonDynamoDBClientBuilder.standard()
			 .withClientConfiguration(helperClass.getClientConfig())
			 .withCredentials(new AWSCredentialsProvider() {
				
				@Override
				public void refresh() {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public AWSCredentials getCredentials() {
					
					return helperClass.getBasicAWSCredtials();
				}
			})
			 .withRegion(Regions.AP_SOUTH_1)
			 .build();
	 
	 System.out.println("client created successfully");
		}
    return client;
}
	public void shutDownClientDynamoClient(){
		if(client != null){
			System.out.println("shutting down client");
		client.shutdown();
		}
	}

}
