package com.creditcloud.brick.task;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Field {
	String 				name;
	String				alias;
	FieldDataType		type;
	Integer				index;
}
