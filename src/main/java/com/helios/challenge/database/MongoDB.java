package com.helios.challenge.database;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

public class MongoDB {
	
	public static MongoDatabase getMongoDB(String databaseName){
		// TODO close this resource ?
		return new MongoClient().getDatabase(databaseName);
	}
	
	public static MongoDatabase getMongoDB(){
//		Properties properties = new Properties();
//		
//		try {
//			properties.load(new FileInputStream("config/mongo.properties"));
//		} catch (IOException e) {
//			e.printStackTrace();
//		} 
//		
//		String host = properties.getProperty("host");
//		int port = Integer.getInteger(properties.getProperty("port"));
//		String databaseName = properties.getProperty("databaseName");
//		
////		String host = System.getProperty("host");
////		int port = Integer.getInteger(System.getProperty("port"));
////		String databaseName = System.getProperty("databaseName");
//		
//		MongoClient mongoClient = new MongoClient(host, port);
//		
//		return mongoClient.getDatabase(databaseName);
		return null;
	}

}
