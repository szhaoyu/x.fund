package com.creditcloud.brick.task;

import static java.nio.file.Files.readAllBytes;
import static java.nio.file.Paths.get;

import java.io.IOException;
import java.util.ArrayList;

import lombok.extern.slf4j.Slf4j;

import com.fasterxml.jackson.databind.ObjectMapper;

@Slf4j
public class CrawlTaskBuilder {
	public static CrawlTask[] buildFromJsonFile(String path){
		//String dir = System.getProperty("user.dir");
    	String 		json;
    	CrawlTask[] tasks=null;

    	try {
			json = new String( readAllBytes( get(path) ) );
	    	ObjectMapper mapper = new ObjectMapper();
	    	tasks = mapper.readValue(json, CrawlTask[].class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error(e.getMessage());
		}

    	return tasks;
	}
}
