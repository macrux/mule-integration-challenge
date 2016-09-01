package org.mule.transformers;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.mule.api.MuleEventContext;
import org.mule.api.lifecycle.Callable;
import org.mule.module.s3.model.S3Object;


public class TestS3Object implements Callable {

	@Override
	public Object onCall(MuleEventContext eventContext) throws Exception {
		S3Object s3Object = (S3Object) eventContext.getMessage().getPayload();
		InputStream stream = s3Object.getObjectContent();
		String line = "";
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
		StringBuilder sb = new StringBuilder();
		while ((line = bufferedReader.readLine()) != null) {
			sb.append(line);
			sb.append("\n");
		}
		bufferedReader.close();
		s3Object.close();
		return sb.toString();
	}

}
