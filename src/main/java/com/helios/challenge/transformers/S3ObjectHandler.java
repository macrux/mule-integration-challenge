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

public class S3ObjectHandler extends AbstractMessageTransformer {
	
	public static final String FILENAME_MSG_PROPERTY = "filename";
	private static final Logger logger = LoggerFactory.getLogger(S3ObjectHandler.class);

	/**
	 * Gets the message payload which is a S3Object. The content of this object
	 * is an InputStream which is processed to obtain the object data into a String.
	 * This String is set as the new message payload and the S3Object's key (the
	 * filename) is set to the Mule Message as property.
	 */
	@Override
	public Object transformMessage(MuleMessage message, String outputEncoding) throws TransformerException {
		BufferedReader bufferedReader = null;
		S3Object s3Object = null;

		try {
			s3Object = (S3Object) message.getPayload();
			// The S3Object content is an InputStream from which
			// a String is created
			InputStream stream = s3Object.getObjectContent();
			String line = "";
			bufferedReader = new BufferedReader(new InputStreamReader(stream));
			StringBuilder xmlStringBuilder = new StringBuilder();
			while ((line = bufferedReader.readLine()) != null) {
				xmlStringBuilder.append(line);
			}
			// Change the full payload to S3Object content
			message.setPayload(xmlStringBuilder.toString());
			return message;
		} catch (Exception e) {
			logger.error("Exception in transform message method: ", e);
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

			if (s3Object != null) {
				try {
					s3Object.close();
				} catch (IOException e) {
					logger.error("Exception closing S3Object: ", e);
					e.printStackTrace();
				}
			}
			
			// Add the filename as a Mule Message property
			message.setProperty(FILENAME_MSG_PROPERTY, s3Object.getKey(), PropertyScope.INVOCATION);
		}

		return message;
	}

}
