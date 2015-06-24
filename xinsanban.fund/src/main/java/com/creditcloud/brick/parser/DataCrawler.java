package com.creditcloud.brick.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.creditcloud.brick.task.CrawlTask;
import com.creditcloud.brick.task.Extractor;
import com.creditcloud.brick.task.Field;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Slf4j
public class DataCrawler {
	String 				url;
	String 				space;
	
	public HashMap<String, Object> doCrawl(CrawlTask task ) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		this.url = task.getUrl();
		this.space = task.getId();
		//
		String content=this.doGet(url);
		if( StringUtils.isNotEmpty(content)) {
			Extractor actor=task.getExtractor();
			if( actor != null )
				this.parse(actor, content, result);
		}
		return result;
	}
	
	public String doGet( String url ) {
		String data=null;
        //return Jsoup.connect(url).userAgent("Mozilla").get();
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet hGet = new HttpGet(url);
		log.info(url);
		CloseableHttpResponse response = null;
		try {
			response = httpclient.execute(hGet);
            HttpEntity entity = response.getEntity();    
            System.out.println(response.getStatusLine());
            log.info( response.getStatusLine().toString() );
            //
            if (entity != null) {  
                System.out.println("Response content length: " + entity.getContentLength());
                data = EntityUtils.toString(entity);
                System.out.println(data);  
                EntityUtils.consume(entity);
                
            }
        }
		catch(Exception e){
			log.error(e.getMessage());
		}
		//response
		try 
		{  
        	if( response != null )
        		response.close();  
        }
		catch(Exception e) {
			log.error(e.getMessage());
		}
		//httpclient
		try {
			if( httpclient != null )
				httpclient.close();
		} catch (Exception e) {
			log.error(e.getMessage());
		}

		return data;
	}
	
	public String removeHtmlLabel( String input ) {
		return input.replaceAll("<[^>]+>", "").replaceAll("&nbsp;"," ").trim();
	}
	
	//
	public ArrayList<String> match( Extractor extractor, String input ) {
		ArrayList<String> result = new ArrayList<String>();
		switch (extractor.getType()) {
		case css:	//call css
		{
			Document doc = Jsoup.parse(input);
			Elements elems = doc.select(extractor.getPattern());
			for( Element elem:elems ) {
				result.add( elem.toString() );
			}
		}
		break;
		case regex: //call regex
		{
			Pattern p = Pattern.compile( extractor.getPattern());
			Matcher m = p.matcher( input );
			String matchValue = null;
			while(m.find()) {
				matchValue = StringEscapeUtils.unescapeHtml4( m.group());
				result.add(matchValue);
			}
		}
		break;
		case empty:
			result.add(input);
			break;
		}
		return result;
	}
	
	public void parse( Extractor extractor, String input, HashMap<String, Object> result ) {
		//1. match by css or regex
		ArrayList<String> strlist = this.match(extractor, input);
		if( strlist.isEmpty() ) {
			//result.put( extractor.getId(), null);
			return;
		}
		//2. call children extractors
		switch(extractor.getData()) {
		case array:{
			//result.setType(ResultDataType.array);
			ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
			for( String str:strlist ) {
				HashMap<String, Object> childResult = new HashMap<String, Object>();
				for( Extractor one:extractor.getChildren()) {
					this.parse(one, str, childResult);
				}
				if( childResult.isEmpty() == false )
					list.add(childResult);
			}
			if(list.isEmpty() == false )
				result.put( extractor.getId(), list );
		}
		break;
		case field:{
			for(Field fd:extractor.getFields()) {
				String val=strlist.get( fd.getIndex() );
				result.put( fd.getName(), this.removeHtmlLabel(val) );
			}
		}
		break;
		case none: {
			for( String str:strlist ) {
				for( Extractor one:extractor.getChildren()) {
					this.parse(one, str, result);
				}
			}
		}
		break;
		}
	}
}
