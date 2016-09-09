package com.helios.challenge.transformers.test;

import org.mule.DefaultMuleMessage;
import org.mule.api.MuleMessage;
import org.mule.api.transformer.Transformer;
import org.mule.transformer.AbstractTransformerTestCase;

import com.helios.challenge.transformers.XmlToJsonTransformer;

public class XmlToJsonTransformerTest extends AbstractTransformerTestCase {

	@Override
	public Object getResultData() {
		return "{\"catalog\":{\"book\":{\"id\":\"100\",\"author\":\"Gambardella, Matthew\",\"title\":\"XML Developer's Guide\"}}}";
	}

	@Override
	public Transformer getRoundTripTransformer() throws Exception {
		return null;
	}

	@Override
	public Object getTestData() {
		String xmlString = "<?xml version=\'1.0\'?><catalog><book><id>100</id><author>Gambardella, Matthew</author><title>XML Developer's Guide</title></book></catalog>";
//		S3Object s3Object = new S3Object();
//		InputStream in = new ByteArrayInputStream(xmlString.getBytes());
//		s3Object.setObjectContent(in);
		
		MuleMessage message = new DefaultMuleMessage(xmlString, null);
//		message.setPayload(xmlString);
		return message;
	}

	@Override
	public Transformer getTransformer() throws Exception {
		XmlToJsonTransformer transformer = new XmlToJsonTransformer();
		return transformer;
	}

}
