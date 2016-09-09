package com.helios.challenge.service;

import static com.helios.challenge.constants.FlowConstants._id;
import static com.helios.challenge.constants.FlowConstants.name;
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

public class MongoDbDocumentServiceImpl implements IDocumentService {

	private static final Logger logger = LoggerFactory.getLogger(MongoDbDocumentServiceImpl.class);
	private MongoCollection<Document> mCollection;

	public MongoDbDocumentServiceImpl() {
		mCollection = new MongoDB().getCollection();
	}
	
	public MongoDbDocumentServiceImpl(String database, String collection){
		mCollection = new MongoDB(database, collection).getCollection();
	}

	@Override
	public void insertDocument(String document) {
		JSONObject jsonObject = new JSONObject(document);
		String documentName = (String) jsonObject.get(name.toString());
		try {
			if (!documentExits(documentName)) {
				mCollection.insertOne(Document.parse(document));
			} else {
				logger.warn("the document with name {} already exists in database. It will be replaced",
						documentName);
				deleteDocument(documentName);
				mCollection.insertOne(Document.parse(document));
			}
		} catch (MongoException e) {
			logger.error("Exception inserting document: ", document, e);
		}
	}

	@Override
	public boolean documentExits(String documentName) {
		long count = mCollection.count(eq(name.toString(), documentName));
		return count > 0;
	}

	@Override
	public void deleteDocument(String documentName) {
		mCollection.deleteOne(eq(name.toString(), documentName));
	}

	@Override
	public String getDocumentByName(String filename) {
		FindIterable<Document> iterable = mCollection.find(eq(name.toString(), filename)).limit(1);
		Document document = iterable.first();
		if (document != null) {
			document.remove(_id.toString());
			return document.toJson();
		} else {
			return "";
		}
	}

	@Override
	public List<String> getAllDocumentNames() {
		ArrayList<String> documentNames = new ArrayList<String>();
		FindIterable<Document> iterable = mCollection.find();
		iterable.forEach(new Block<Document>() {
			public void apply(final Document document) {
				documentNames.add(document.get(name.toString()).toString());
			}
		});
		return documentNames;
	}

}
