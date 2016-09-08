package com.helios.challenge.transformers;

import static com.helios.challenge.constants.FlowConstants.filename;

import org.json.JSONObject;
import org.json.XML;
import org.mule.api.MuleMessage;
import org.mule.api.transformer.TransformerException;
import org.mule.api.transport.PropertyScope;
import org.mule.transformer.AbstractMessageTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helios.challenge.constants.FlowConstants;
import com.mulesoft.mmc.agent.v3.dto.NullPayload;

public class XmlToJsonTransformer extends AbstractMessageTransformer {

	
	private static final Logger logger = LoggerFactory.getLogger(XmlToJsonTransformer.class.getName());

	/**
	 * Receives a MuleMessage from the flow and extracts the payload which is a
	 * XML encoded data. The payload will be transformed from XML-JSON. If any
	 * exception occurs along the process, the message payload will be set as a
	 * NullPayload.
	 */
	@Override
	public Object transformMessage(MuleMessage message, String outputEncoding) throws TransformerException {

		try {
			String xmlString = (String) message.getPayload();
			JSONObject jsonObject = XML.toJSONObject(xmlString);
			String fileNameProperty = message.getProperty(filename.toString(), PropertyScope.INVOCATION);
			// Add filename as a key to the JSON object
			jsonObject.put(FlowConstants.name.toString(), fileNameProperty);
			// Change payload to JSON String
			message.setPayload(jsonObject.toString());
			// Add the filename as a Mule Message property
			return message;
		} catch (Exception e) {
			logger.error("Exception in transform message method: ", e);
			message.setPayload(new NullPayload());
		}
		return message;
	}

}
