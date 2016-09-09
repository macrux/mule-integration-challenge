package com.helios.challenge.datalayer;

import static com.helios.challenge.constants.FlowConstants.filename;

import org.mule.api.MuleEventContext;
import org.mule.api.lifecycle.Callable;
import org.mule.api.transport.PropertyScope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helios.challenge.service.IDocumentService;
import com.helios.challenge.service.MongoDbDocumentServiceImpl;

public class DataHandler implements Callable {

	private static final Logger logger = LoggerFactory.getLogger(DataHandler.class);

	/**
	 * Extract the JSON payload from the MuleMessage notified through the mule
	 * event and store it in the database.
	 */
	@Override
	public Object onCall(MuleEventContext eventContext) throws Exception {
		IDocumentService databaseService = new MongoDbDocumentServiceImpl();
		String filenameProperty = eventContext.getMessage().getProperty(filename.toString(), PropertyScope.INVOCATION);
		String jsonString = eventContext.getMessage().getPayloadAsString();
		logger.info("Inserting file " + filenameProperty + " into mongodb");
		databaseService.insertDocument(jsonString);
		return eventContext.getMessage();
	}

}
