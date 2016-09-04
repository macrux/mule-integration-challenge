package com.helios.challenge.database;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

public class MongoDB {
	
	public static MongoDatabase getMongoDB(String databaseName){
		// TODO close this resource ?
		return new MongoClient().getDatabase(databaseName);
	}

}
