package com.creditcloud.brick.task;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

import com.mongodb.DBObject;

import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Data
@ToString
@Slf4j
public class CrawlTask {
	String 						name;
	String						id;
	String 						url;
	ArrayList<KeyField>  		keys;
	ArrayList<FreezeCondition>  freeze;
	Extractor			 		extractor;
	
	public String generateQueryObject( HashMap<String, Object> data) {
		boolean first=true;
		StringBuilder builder = new StringBuilder();
		builder.append("{");
		for( KeyField key:keys ) {
			if( !first )
				builder.append(", ");
			else
				first = false;
			//
			builder.append( "\"").append(key.getId()).append("\"").append(":");
			Object val=data.get(key.getId());
			if( val instanceof Integer || val instanceof Float ) {
				builder.append(val);
			}
			else {
				builder.append("\"").append(val).append("\"");
			}
			
		}//!for
		builder.append("}");

		log.info( builder.toString() );

		return builder.toString();
	}
	
	public boolean checkFreeze(HashMap<String, Object> data ) {
		boolean result = false;
		for( FreezeCondition one:freeze ) {
			Object val=data.get(one.getId());
			if( val.equals(one.getValue()) ) {
				result = true;
				break;
			}
		}
		return result;
	}
	
	public boolean checkFreeze(DBObject obj ) {
		boolean result = false;
		for( FreezeCondition one:freeze ) {
			Object val=obj.get(one.getId());
			if( val.equals(one.getValue()) ) {
				result = true;
				break;
			}
		}
		return result;
	}
}
