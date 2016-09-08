package com.helios.challenge.datalayer;

import static com.helios.challenge.constants.ChallengeConstants.FILENAME_MULE_MSG_PROPERTY;

import org.mule.api.MuleEventContext;
import org.mule.api.lifecycle.Callable;
import org.mule.api.transport.PropertyScope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helios.challenge.service.IDocumentService;
import com.helios.challenge.service.MongoDbDocumentServiceImpl;
import com.mulesoft.mmc.agent.v3.dto.NullPayload;

public class DataHandler implements Callable {
	
	private static final Logger logger = LoggerFactory.getLogger(DataHandler.class);
	
	@Override
	public Object onCall(MuleEventContext eventContext) throws Exception {
		IDocumentService databaseService = new MongoDbDocumentServiceImpl();
		String filename = eventContext.getMessage().getProperty(FILENAME_MULE_MSG_PROPERTY, PropertyScope.INVOCATION);
		if(!(eventContext.getMessage().getPayload() instanceof NullPayload)){
			String jsonString = eventContext.getMessage().getPayloadAsString();
			logger.info("Inserting file "+filename+" into mongodb");
			databaseService.insertDocument(jsonString);
		} else {
			logger.warn("The message payload for the file "+filename+" is null");
		}
		
		return eventContext.getMessage();
	}

}
