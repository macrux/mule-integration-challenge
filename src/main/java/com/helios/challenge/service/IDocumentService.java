package com.helios.challenge.service;

import java.util.List;

public interface IDocumentService {

	/**
	 * Inserts a new document into the database. If a document with the
	 * same name exits, it will be replaced.
	 * @param document a JSON/BSON encoded String.
	 */
	void insertDocument(String document);
	
	/**
	 * Checks if a document with the given name already exits in the database.
	 * @param documentName the name of the document.
	 * @return true if the document exists in the database, false otherwise.
	 */
	boolean documentExits(String documentName);
	
	/**
	 * Deletes a document the given name from the database.
	 * @param documentName the name of the document.
	 */
	void deleteDocument(String documentName);
	
	/**
	 * Finds and returns a document (a JSON encoded String) with the given name
	 * from the database.
	 * @param documentName the document name.
	 * @return a JSON encoded String if the document exists. An empty String otherwise.
	 */
	String getDocumentByName(String documentName);
	
	/**
	 * Gets a list of all document names stored in the database.
	 * @return a List with the document names. An empty list if there are not documents.
	 */
	List<String> getAllDocumentNames();
	
}
