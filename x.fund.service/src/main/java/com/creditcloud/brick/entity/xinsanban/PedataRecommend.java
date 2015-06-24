package com.creditcloud.brick.entity.xinsanban;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@ToString
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "pedata")
public class PedataRecommend {
	@Id
	String 		_id;
	String 		org_url;
	String 		amount;
	String  	org;
	String 		name;
	String      raising_begin;
	String 		status;
}
