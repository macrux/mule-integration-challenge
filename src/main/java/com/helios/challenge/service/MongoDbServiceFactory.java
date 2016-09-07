package com.helios.challenge.service;

public class MongoDbServiceFactory {

	private static IDocumentService service = null;
	
	public static IDocumentService getService() {
		if (service == null ){
			service = new MongoDbDocumentServiceImpl();
		} 
		return service;
	}
}
