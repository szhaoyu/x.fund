package com.creditcloud.brick;

import static java.nio.file.Files.readAllBytes;
import static java.nio.file.Paths.get;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map.Entry;

import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.creditcloud.brick.parser.DataCrawler;
import com.creditcloud.brick.repository.MongoJsonRepository;
import com.creditcloud.brick.task.CrawlTask;
import com.creditcloud.brick.task.CrawlTaskBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.DBObject;

@SpringBootApplication
@Slf4j
public class App implements CommandLineRunner{
	
    public static void main( String[] args )
    {
    	SpringApplication.run(App.class, args);
    }
    
    @Override
    public void run( String... strings) throws Exception {
    	//1. build crawling task
    	CrawlTask[] tasks = CrawlTaskBuilder.buildFromJsonFile("task.json");
    	DataCrawler crawler=new DataCrawler();
    	for( CrawlTask task:tasks ) {
    		//2. execute crawling&parsing according task definition. 
    		HashMap<String, Object> result = crawler.doCrawl(task);
    		//
    		if( result.size() == 0 || result.size() == 1 && (result.values().isEmpty()||result.values().toArray()[0]==null) ) {
    			log.info( crawler.getUrl()+". crawled 0 items. "+new Date().toLocaleString() );
    			continue;
    		}
    		//3. persisted parsed results( here MongoDB )
    		if( result.size() == 1 ) {
    			for( Entry<String, Object> entry:result.entrySet() ) {
	    			//HashMap<String, Object>[] items = (HashMap<String, Object>[]) result.values().toArray();
    				ArrayList<HashMap<String, Object>> items = (ArrayList<HashMap<String, Object>>)entry.getValue();
	    			for(HashMap<String, Object> item:items ) {
	    				this.saveData( task, item);
	    			}//!for
    			}
    		}
    		else {
				this.saveData(task, result);
			}
    	}
    }
    
    public void saveData(CrawlTask task, HashMap<String, Object> item ) {
    	ObjectMapper mapper = new ObjectMapper();
    	//
    	String  keyStr=task.generateQueryObject(item);
		DBObject obj=MongoJsonRepository.findByKeys( "xsb_fund", task.getId(), keyStr);
		//check already if existed ...
		if( obj!=null && task.checkFreeze(obj) )
			return;
		
		String json="";
		try{
			json = mapper.writeValueAsString( item );
			MongoJsonRepository.saveJsonData("xsb_fund", task.getId(), json, obj );
		}
		catch(Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		//log.info("find object:"+obj==null?"null":obj.toString());
		log.info(json);
    }
}
