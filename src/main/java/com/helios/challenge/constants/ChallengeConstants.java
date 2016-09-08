package com.helios.challenge.constants;

@Deprecated
/*
 * Use FlowConstans instead this class.
 */
public class ChallengeConstants {

	/**
	 * key name used by MongoDB to store the unique document ID
	 */
	public static final String _ID_MONGO_KEY 				= "_id";
	/**
	 * key name used to store a document's name in JSON
	 */
	public static final String FILENAME_JSON_KEY 			= "name";
	/**
	 * Property name to store a the file name in a Mule Message
	 */
	public static final String FILENAME_MULE_MSG_PROPERTY 	= "filename";
}
