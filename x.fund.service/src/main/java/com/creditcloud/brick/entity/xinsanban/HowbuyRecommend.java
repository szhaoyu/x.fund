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
@Document(collection = "howbuy")
public class HowbuyRecommend {
	@Id
	String 		_id;
	String 		org;
	String 		invest;
	String 		name;
	String      term;
	String 		profit;
	String 		url;
	String 		status;
}
