package com.helios.challenge.transformers.test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.mule.api.transformer.Transformer;
import org.mule.transformer.AbstractTransformerTestCase;

import com.amazonaws.services.s3.model.S3Object;
import com.helios.challenge.transformers.S3ObjectHandler;

public class S3ObjectHandlerTckTest extends AbstractTransformerTestCase {
	
	public static final String TEST_DATA = "<?xml version=\"1.0\" ?>"
										  +"<festivities>"
										  +"	<festivity>"  
										  +"		<name>NESTOR's event</name>"
										  +"		<place>Marin's castle</place>"
										  +"		<start>2016-02-22T19:16:01.001Z</start>"
										  +"		<end>2016-07-30T19:16:01.001Z</end>"
										  +"	</festivity>"
										  +"</festivities>";
	
	@Override
	public Object getResultData() {
		return TEST_DATA;
	}

	@Override
	public Transformer getRoundTripTransformer() throws Exception {
		return new S3ObjectHandler();
	}

	@Override
	public Object getTestData() {
		S3Object s3Object;
		String filename = "festivites2.xml";
		InputStream inputStream;
		
		s3Object = new S3Object();
		s3Object.setKey(filename);
//		inputStream = S3ObjectHandler.class.getClassLoader().getResourceAsStream(filename);
		inputStream = new ByteArrayInputStream(TEST_DATA.getBytes());
		s3Object.setObjectContent(inputStream);
		return s3Object;
	}

	@Override
	public Transformer getTransformer() throws Exception {
		return new S3ObjectHandler();
	}

}
