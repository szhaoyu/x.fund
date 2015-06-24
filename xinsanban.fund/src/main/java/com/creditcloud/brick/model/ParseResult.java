package com.creditcloud.brick.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ParseResult {
	ResultDataType		type;
	String				key;
	Object				data;		//ArrayList or HashMap<String,Object>
}
