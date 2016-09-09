package com.helios.challenge.database;

import static com.helios.challenge.constants.FlowConstants.COLLECTION;
import static com.helios.challenge.constants.FlowConstants.DATABASE;
import static com.helios.challenge.constants.FlowConstants.HOST;
import static com.helios.challenge.constants.FlowConstants.PORT;
import static com.helios.challenge.constants.FlowConstants.name;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Properties;

import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class MongoDB {

	private static final Logger logger = LoggerFactory.getLogger(MongoDB.class);
	private final String mDATABASE;
	private final String mCOLLECTION;
	private Properties props;
	
	private MongoClient mongoClient = null;
	
	public MongoDB(){
		loadProperties();
		mDATABASE = props.getProperty(DATABASE.name());
		mCOLLECTION = props.getProperty(COLLECTION.name());
	}
	
	public MongoDB(String database, String collection) {
		loadProperties();
		mDATABASE = database;
		mCOLLECTION = collection;
	}

	private void loadProperties() {
		props = new Properties();
		try {
			props.load(new FileInputStream("config/mongo.properties"));
		} catch (FileNotFoundException e) {
			logger.error("Exception reading mongo.properties file: ", e);
		} catch (Exception e) {
			logger.error("Exception reading mongo.properties file: ", e);
		}
	}
	
	public MongoClient getMongoClient(){
		if (mongoClient == null) {
			String hostProperty = props.getProperty(HOST.name());
			int portProperty = Integer.valueOf(props.getProperty(PORT.name()));
			mongoClient = new MongoClient(hostProperty, portProperty);
			logger.info("New MongoClient connected to {}", mongoClient.getConnectPoint());
		}
		
		return mongoClient;
	}
	
	public MongoDatabase getMongoDB() {
		return getMongoClient().getDatabase(mDATABASE);
	}

	public MongoCollection<Document> getCollection() {
		MongoCollection<Document> collection = getMongoDB().getCollection(mCOLLECTION);
		// add filename as unique index in the collection
		collection.createIndex(new Document(name.toString(), 1));
		return collection;
	}
	
	public void dropDatabase(){
		if(mongoClient != null){
			mongoClient.dropDatabase(mDATABASE);
			logger.info("Database {} was dropped", mDATABASE);
		}
	}
	
	public void closeClient(){
		if(mongoClient != null){
			mongoClient.close();
		}
	}

}
