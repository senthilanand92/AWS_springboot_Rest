package main.java.com.amazonaws.samples.models;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;

@DynamoDBDocument
public class Address {
	String streetName;
	int doorno;
	String city;
	
	public Address(){
		
	}
	
	public Address(String streetName, int doorno, String city) {
		super();
		this.streetName = streetName;
		this.doorno = doorno;
		this.city = city;
	}
	@DynamoDBAttribute(attributeName = "StreetName")
	public String getStreetName() {
		return streetName;
	}
	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}
	@DynamoDBAttribute(attributeName = "doorNo")
	public int getDoorno() {
		return doorno;
	}
	public void setDoorno(int doorno) {
		this.doorno = doorno;
	}
	@DynamoDBAttribute(attributeName = "cityName")
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}

}
