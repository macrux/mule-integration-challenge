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
	private static final String DATABASE_NAME = "test";
	private static final String COLLECTION_NAME = "filetest";
	
	private static MongoClient mongoClient = null;

	public static MongoDatabase getMongoDB() {
		if (mongoClient == null) {
			mongoClient = new MongoClient();
			logger.info("New MongoClient connected to {}", mongoClient.getConnectPoint());
		}

		return mongoClient.getDatabase(DATABASE_NAME);
	}

	public static MongoCollection<Document> getCollection() {
		MongoCollection<Document> collection = getMongoDB().getCollection(COLLECTION_NAME);
		// add filename as unique index in the collection
		collection.createIndex(new Document(FILENAME_JSON_KEY, 1));
		return collection;
	}

}
