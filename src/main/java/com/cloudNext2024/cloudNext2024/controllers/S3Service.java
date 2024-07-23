package com.cloudNext2024.cloudNext2024.controllers;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;

import lombok.Data;

@Data
@Service
public class S3Service {

	private final AmazonS3 s3Client;

	@Value("${S3_BUCKET_NAME}")
	private String bucketName;

	public S3Service(@Value("${AWS_ACCESS_KEY_ID}") String awsId, @Value("${AWS_SECRET_ACCESS_KEY}") String awsKey,
			@Value("${AWS_REGION}") String region) {
		BasicAWSCredentials awsCreds = new BasicAWSCredentials(awsId, awsKey);
		this.s3Client = AmazonS3ClientBuilder.standard().withRegion(region)
				.withCredentials(new AWSStaticCredentialsProvider(awsCreds)).build();
	}

	public boolean uploadFile(String base64String, String fileName) {
		// Serializar o arquivo em Base64
//        Base64 base64 = new Base64();
//        String encodedFile = base64.encodeAsString(file.getBytes());
		
//    	try {
		
			
		Base64 base64 = new Base64(); // Deserializar o arquivo de Base64
		byte[] byteImg = Base64.decodeBase64(base64String); // converter em binário

		InputStream fis = new ByteArrayInputStream(byteImg);// salva em memoria RAM
		
		ObjectMetadata metadata = new ObjectMetadata(); // metadados do arquivo
				
		this.s3Client.putObject("cloudnext24", fileName, fis, metadata); // nome do bucket cloudnext24
		
		return true;
		
//		https://stackoverflow.com/questions/30939904/uploading-base64-encoded-image-to-amazon-s3-using-java
		
//    	catch (Exception e) {
//			
//		}

//        Base64.getDecoder().decode(encoded);
		// Colocar o arquivo codificado no S3
		
	}

	public byte[] downloadFile(String key) {
		// Baixar o arquivo do S3
		String encodedFile = s3Client.getObjectAsString(bucketName, key);

		Base64 base64 = new Base64();
		return base64.decode(encodedFile);
	}
}