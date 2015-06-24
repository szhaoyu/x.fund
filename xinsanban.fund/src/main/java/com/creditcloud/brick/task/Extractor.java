package com.creditcloud.brick.task;

import java.util.ArrayList;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
public class Extractor {
	String 					id;
	ExtractorType			type;
	String					pattern;
	ExtractorDataType		data;
	ArrayList<Extractor>	children=null;
	ArrayList<Field> 		fields=null;
}
