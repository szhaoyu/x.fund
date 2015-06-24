package com.creditcloud.brick.task;

import java.util.HashMap;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FreezeCondition {
	String 			id;
	String 			value;

	public boolean check(HashMap<String, Object> data ) {
		Object val=data.get(id);
		if( val!= null && val instanceof String && val.equals(value) )
			return true;
		return false;
	}
}
