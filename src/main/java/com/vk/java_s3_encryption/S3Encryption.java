package com.vk.java_s3_encryption;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.SSEAlgorithm;
import com.amazonaws.services.s3.model.SSEAwsKeyManagementParams;

/**
 * Put encrypted object into S3 (Server side encryption)
 *
 */
public class S3Encryption 
{
	private static final String ACCESS_KEY = "";
	private static final String SECRET_KEY = "";
	
	public static void saveEncryptedObject(){
		AWSCredentials awsCredentials = new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY);
		AmazonS3 amazonS3 = new AmazonS3Client(awsCredentials);
		String content = "This is an example text";
		byte[] bytes = content.getBytes();
		ObjectMetadata metaData = new ObjectMetadata();
		metaData.setSSEAlgorithm(SSEAlgorithm.KMS.getAlgorithm());
		InputStream inputStream = new ByteArrayInputStream(bytes);
		PutObjectRequest putObjectRequest = new PutObjectRequest("bucketname", "key", inputStream, metaData);
		putObjectRequest.withSSEAwsKeyManagementParams(new SSEAwsKeyManagementParams("YOUR_KMS_KEY"));
		try{
			amazonS3.putObject(putObjectRequest);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
    public static void main( String[] args ){
    	saveEncryptedObject();
    }
}
