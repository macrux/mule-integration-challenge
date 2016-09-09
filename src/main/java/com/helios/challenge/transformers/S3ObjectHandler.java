package com.helios.challenge.transformers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.mule.api.MuleMessage;
import org.mule.api.transformer.TransformerException;
import org.mule.api.transport.PropertyScope;
import org.mule.module.s3.model.S3Object;
import org.mule.transformer.AbstractMessageTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helios.challenge.constants.FlowConstants;
import com.mulesoft.mmc.agent.v3.dto.NullPayload;

public class S3ObjectHandler extends AbstractMessageTransformer {

	private static final Logger logger = LoggerFactory.getLogger(S3ObjectHandler.class);

	/**
	 * Gets the message payload which is a S3Object. The content of this object
	 * is an InputStream which is processed to obtain the object data into a
	 * String. This String is set as the new message payload and the S3Object's
	 * key (the filename) is set to the Mule Message as property.
	 */
	@Override
	public Object transformMessage(MuleMessage message, String outputEncoding) throws TransformerException {
		S3Object s3Object = (S3Object) message.getPayload();
		// The S3Object content is an InputStream from which
		// a String is created after processing the InputStrem
		String xmlString = processInputStream(s3Object.getObjectContent());
		// Change the full payload to S3Object content
		if(!xmlString.isEmpty()){
			message.setPayload(xmlString);	
		} else {
			//TODO should it throw nullpayloadexception instead set an empty string payload?
			message.setPayload(new NullPayload());
		}

		if (s3Object != null) {
			try {
				s3Object.close();
			} catch (IOException e) {
				logger.error("Exception closing S3Object: ", e);
				e.printStackTrace();
			}
		}
		// Add the filename as a Mule Message property
		message.setProperty(FlowConstants.filename.toString(), s3Object.getKey(), PropertyScope.INVOCATION);
		return message;
	}

	private String processInputStream(InputStream inputStream) {
		BufferedReader bufferedReader = null;
		try {
			String line = "";
			bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
			StringBuilder xmlStringBuilder = new StringBuilder();
			while ((line = bufferedReader.readLine()) != null) {
				xmlStringBuilder.append(line);
			}
			return xmlStringBuilder.toString();
		} catch (Exception e) {
			logger.error("Exception processing InputStream: ", e);
		} finally {
			// Resources must be closed
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException e) {
					logger.error("Exception closing BufferedReader: ", e);
					e.printStackTrace();
				}
			}
		}
		
		return "";
	}

}
