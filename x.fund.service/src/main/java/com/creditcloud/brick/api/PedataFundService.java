package com.creditcloud.brick.api;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.creditcloud.brick.entity.xinsanban.PedataRecommend;
import com.creditcloud.brick.repository.xinsanban.PedataRecommandRepository;

@RestController
@RequestMapping(value="/api/v1/fund/xinsanban/pedata")
@Slf4j
public class PedataFundService {
	@Autowired
	PedataRecommandRepository		repo;
	@Autowired
	MongoTemplate				template;
	
	@RequestMapping(value="/all", method=RequestMethod.GET)
	public ResponseEntity<?> queryAll( ) {
		List<PedataRecommend> list = repo.findAll();
		
		
		return new ResponseEntity<>(list, null, HttpStatus.OK);
	}
	
	@RequestMapping(value="/open", method=RequestMethod.GET)
	public ResponseEntity<?> queryOpen( ) {
		List<PedataRecommend> list = template.find( 
				new Query(new Criteria("status").regex("募集")), 
				PedataRecommend.class );
		
		return new ResponseEntity<>(list, null, HttpStatus.OK);
	}
}
