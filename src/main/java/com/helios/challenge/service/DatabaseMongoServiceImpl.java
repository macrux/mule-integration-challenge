package com.helios.challenge.service;

import static com.helios.challenge.constants.ChallengeConstants.FILENAME_JSON_KEY;
import static com.helios.challenge.constants.ChallengeConstants._ID_MONGO_KEY;
import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helios.challenge.database.MongoDB;
import com.mongodb.Block;
import com.mongodb.MongoException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class DatabaseMongoServiceImpl implements IDatabaseService {

	private static final Logger logger = LoggerFactory.getLogger(DatabaseMongoServiceImpl.class);

	private static final String DATABASE_NAME = "test";
	private static final String COLLECTION_NAME = "filetest";

	private MongoDatabase database = MongoDB.getMongoDB(DATABASE_NAME);
//	private MongoDatabase database = MongoDB.getMongoDB();
	private MongoCollection<Document> collection = database.getCollection(COLLECTION_NAME);

	@Override
	public void insertDocument(String document) {
		JSONObject jsonObject = new JSONObject(document);
		String documentName = (String) jsonObject.get(FILENAME_JSON_KEY);
		try {
			if (!documentExits(documentName)) {
				collection.insertOne(Document.parse(document));
			} else {
				logger.warn("the document with name {} already exists in database. It is goint to be replaced",
						documentName);
				deleteDocument(documentName);
				collection.insertOne(Document.parse(document));
			}
		} catch (MongoException e) {
			logger.error("Exception inserting document: ", document, e);
		}
	}

	@Override
	public boolean documentExits(String documentName) {
		long count = collection.count(eq(FILENAME_JSON_KEY, documentName));
		return count > 0;
	}

	@Override
	public void deleteDocument(String documentName) {
		collection.deleteOne(eq(FILENAME_JSON_KEY, documentName));
	}

	@Override
	public String getDocumentByName(String filename) {
		FindIterable<Document> iterable = collection.find(eq(FILENAME_JSON_KEY, filename));
		Document document = iterable.first();
		if (document != null) {
			document.remove(_ID_MONGO_KEY);
			return document.toJson();
		} else {
			return "";
		}
	}

	@Override
	public List<String> getAllDocumentNames() {
		ArrayList<String> documentNames = new ArrayList<String>();
		FindIterable<Document> iterable = collection.find();
		iterable.forEach(new Block<Document>() {
			public void apply(final Document document) {
				documentNames.add(document.get(FILENAME_JSON_KEY).toString());
			}
		});
		return documentNames;
	}

}
