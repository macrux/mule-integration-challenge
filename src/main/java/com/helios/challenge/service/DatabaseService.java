package com.helios.challenge.service;

public interface DatabaseService {

	void insertDocument(String	document);
	boolean documentExits(String documentName);
	void deleteDocument(String documentName);
	
}
