package com.helios.challenge.database;

import static com.helios.challenge.constants.ChallengeConstants.FILENAME_JSON_KEY;

import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class MongoDB {

	private static final Logger logger = LoggerFactory.getLogger(MongoDB.class);
	private final String DATABASE;
	private final String COLLECTION;
	
	private MongoClient mongoClient = null;
	
	public MongoDB(){
		DATABASE = "challenge";
		COLLECTION = "files";
	}
	
	public MongoDB(String database, String collection) {
		DATABASE = database;
		COLLECTION = collection;
	}

	public MongoClient getMongoClient(){
		if (mongoClient == null) {
			mongoClient = new MongoClient();
			logger.info("New MongoClient connected to {}", mongoClient.getConnectPoint());
		}
		
		return mongoClient;
	}
	
	public MongoDatabase getMongoDB() {
		return getMongoClient().getDatabase(DATABASE);
	}

	public MongoCollection<Document> getCollection() {
		MongoCollection<Document> collection = getMongoDB().getCollection(COLLECTION);
		// add filename as unique index in the collection
		collection.createIndex(new Document(FILENAME_JSON_KEY, 1));
		return collection;
	}
	
	public void dropDatabase(){
		if(mongoClient != null){
			mongoClient.dropDatabase(DATABASE);
		}
	}
	
	public void closeClient(){
		if(mongoClient != null){
			mongoClient.close();
		}
	}

}
