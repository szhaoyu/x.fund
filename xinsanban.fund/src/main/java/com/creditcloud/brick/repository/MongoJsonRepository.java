package com.creditcloud.brick.repository;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.util.JSON;

public class MongoJsonRepository {
	String url;
	String user;
	String passwd;
	static Mongo   mongo = new Mongo();

	public static void saveJsonData(String dbName, String space, String data, DBObject prev ) {
		DB db=mongo.getDB( dbName );
		DBCollection q=db.getCollection(space);
		DBObject obj=(BasicDBObject) JSON.parse(data);
		if( prev != null ) {
			obj.put("_id", prev.get("_id"));
		}
		q.save(obj);
	}
	
	public static DBObject findByKeys(String dbName, String space, String keys ) {
		DB db=mongo.getDB( dbName );
		DBCollection q=db.getCollection(space);
		DBObject obj=(BasicDBObject) JSON.parse(keys);
		DBCursor cursor = q.find(obj);
		
		if( cursor.hasNext() )
			return cursor.next();
		
		return null;
	}
	
}
