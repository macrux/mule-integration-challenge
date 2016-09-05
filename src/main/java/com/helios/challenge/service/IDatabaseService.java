package com.helios.challenge.service;

import java.util.List;

public interface IDatabaseService {

	void insertDocument(String	document);
	boolean documentExits(String documentName);
	void deleteDocument(String documentName);
	String getDocumentByName(String filename);
	List<String> getAllDocumentNames();
	
}
