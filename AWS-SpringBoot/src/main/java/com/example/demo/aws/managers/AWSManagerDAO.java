package com.example.demo.aws.managers;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.CreateTableResult;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.example.demo.aws.factory.AWSClientFactory;
import com.example.demo.aws.models.Employee;

@Component
public class AWSManagerDAO {
	
	@Autowired
	private AWSClientFactory clientFactory;
	
	
	public DynamoDBMapper mapper =null; 
	
	 @PostConstruct
	    public void init(){
			this.mapper = new DynamoDBMapper(clientFactory.initializeClient());
	    }
	public void createTable( String tableName) throws AmazonServiceException,AmazonClientException{
		System.out.println("Attempting to create table; please wait...");
		
		
		 List<AttributeDefinition> attributeDefinitions = new ArrayList<AttributeDefinition>();
         attributeDefinitions.add(new AttributeDefinition().withAttributeName("EMP_ID").withAttributeType("N"));

         List<KeySchemaElement> keySchema = new ArrayList<KeySchemaElement>();
         keySchema.add(new KeySchemaElement().withAttributeName("EMP_ID").withKeyType(KeyType.HASH)); // Partition
                                                                                                  // key

         CreateTableRequest createTableRequest = new CreateTableRequest().withTableName(tableName).withKeySchema(keySchema)
             .withAttributeDefinitions(attributeDefinitions).withProvisionedThroughput(
                 new ProvisionedThroughput().withReadCapacityUnits(5L).withWriteCapacityUnits(6L));


        CreateTableResult table = clientFactory.initializeClient().createTable(createTableRequest ); // Sort key
        
           
        
        System.out.println("Success.  Table status: " +table.getTableDescription().getTableStatus());
	}
	
	
	public Employee AddEmployee(Employee employee) throws AmazonServiceException,AmazonClientException{
		System.out.println("Attempting to insert Employee record"+employee);
		Employee ifExistingEmployee=mapper.load(Employee.class,employee.getEmpID());
		if(null ==ifExistingEmployee ){
		mapper.save(employee);
		System.out.println("Insertion successful");
		return readEmployee(employee.getEmpID());
		}
		else{
			System.out.println("Employee with ID:"+employee.getEmpID()+" Already Exists");
			return null;
		}
	  
		
	}
	public Employee readEmployee(int Emp_ID) throws AmazonServiceException,AmazonClientException{
		System.out.println("Reading employee with ID: "+Emp_ID);
		Employee employee=mapper.load(Employee.class,Emp_ID);
		
		return employee;
	}
	public Employee UpdateEmployee(int Emp_ID,Employee employee) throws AmazonServiceException,AmazonClientException{
		Employee CheckEmployee=mapper.load(Employee.class,Emp_ID);
		Employee updatedEmployee=null;
		if(null != CheckEmployee) {
		employee.setEmpID(Emp_ID);
		mapper.save(employee);
		updatedEmployee=mapper.load(Employee.class,employee.getEmpID());
		System.out.println("Employee Found and updated..");
	}
		else {
			System.out.println("Employee not found..");
		}
		return updatedEmployee;
		}
//		 DynamoDBMapperConfig config = new DynamoDBMapperConfig(DynamoDBMapperConfig.ConsistentReads.CONSISTENT);
//		 Employee updatedEmployee =mapper.load(Employee.class,employee.getEmpID(),config);
		 
	public Employee DeleteEmployee(int Emp_ID) throws AmazonServiceException,AmazonClientException{
		Employee employee=mapper.load(Employee.class,Emp_ID);
		if(null != employee) {
		mapper.delete(employee);
		DynamoDBMapperConfig config = new DynamoDBMapperConfig(DynamoDBMapperConfig.ConsistentReads.CONSISTENT);
		 Employee deletedEmployee =mapper.load(Employee.class,employee.getEmpID(),config);
       if (deletedEmployee == null) {
           System.out.println("Done - Sample item is deleted.");
       }
		}
		else {
			System.out.println("Employee not found unable to delete");
		}
		return employee; 
	}
	

	public AWSClientFactory getClientFactory() {
		return clientFactory;
	}
	public void setClientFactory(AWSClientFactory clientFactory) {
		this.clientFactory = clientFactory;
	}


}
