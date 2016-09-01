package org.mule.transformers;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.mule.api.MuleEventContext;
import org.mule.api.lifecycle.Callable;

public class ExtractObjectContent implements Callable {

	@Override
	public Object onCall(MuleEventContext eventContext) throws Exception {
		InputStream stream = (InputStream) eventContext.getMessage().getPayload();
		String line = "";
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
		StringBuilder sb = new StringBuilder();
		while ((line = bufferedReader.readLine()) != null) {
			sb.append(line);
			sb.append("\n");
		}
		bufferedReader.close();
		stream.close();
		return sb.toString();
	}

}
