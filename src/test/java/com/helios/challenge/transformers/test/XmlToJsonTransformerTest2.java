package com.helios.challenge.transformers.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.mule.DefaultMuleMessage;
import org.mule.api.MuleContext;
import org.mule.api.MuleMessage;
import org.mule.api.annotations.expressions.Lookup;
import org.mule.api.context.MuleContextAware;
import org.mule.api.transformer.TransformerException;

import com.helios.challenge.transformers.XmlToJsonTransformer;

public class XmlToJsonTransformerTest2 implements MuleContextAware{

	@Lookup
	private MuleContext muleContext;
	
	@Test
	public void testTransformMessageMuleMessageString() throws TransformerException {
		String resultData = "{\"catalog\":{\"book\":{\"id\":\"100\",\"author\":\"Gambardella, Matthew\",\"title\":\"XML Developer's Guide\"}}}";
		String xmlString = "<?xml version=\'1.0\'?><catalog><book><id>100</id><author>Gambardella, Matthew</author><title>XML Developer's Guide</title></book></catalog>";
		XmlToJsonTransformer transformer = new XmlToJsonTransformer();
		MuleMessage message = new DefaultMuleMessage(xmlString, muleContext);
//		message.setPayload(xmlString);
		MuleMessage result = (MuleMessage) transformer.transformMessage(message, null);
		assertEquals("test..", resultData, result.getPayload().toString());
		
	}

	@Override
	public void setMuleContext(MuleContext context) {
		System.out.println("Setting MuleContext");
		this.muleContext = context;
	}

}
