package com.helios.challenge.constants;

public enum FlowConstants {
	
	/*
	 * Database properties included in the file mongo.properties
	 */
	HOST, PORT, USER, PASSWORD, DATABASE, COLLECTION, 
	
	/*
	 * key name used by MongoDB to store the unique document ID
	 */
	_id,
	
	/*
	 * key name used to store a document's name in JSON
	 */
	name,
	
	/*
	 * Property name to store a the file name in a Mule Message
	 */
	filename
}
