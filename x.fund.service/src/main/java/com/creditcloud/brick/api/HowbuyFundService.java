package com.creditcloud.brick.api;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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

import com.creditcloud.brick.entity.xinsanban.HowbuyRecommend;
import com.creditcloud.brick.repository.xinsanban.HowbuyRecommendRepository;


@RestController
@RequestMapping(value="/api/v1/fund/xinsanban/howbuy")
@Slf4j
public class HowbuyFundService {
	@Autowired
	HowbuyRecommendRepository   repo;
	@Autowired
	MongoTemplate				template;
	
	@RequestMapping(value="/all", method=RequestMethod.GET)
	public ResponseEntity<?> queryAll(HttpServletRequest request ) {
    	log.info("LocalAddress:"+request.getLocalAddr() );
    	log.info("LocalPort:"+request.getLocalPort());
    	log.info("LocalName:"+request.getLocalName());
    	log.info("RemoteAddress:"+request.getRemoteAddr());
    	log.info("RemoteHost:"+request.getRemoteHost());
    	log.info("RemotePort:"+request.getRemotePort());
    	log.info("RemoteUser:"+request.getRemoteUser());
    	log.info("RequestedSessionID:"+request.getRequestedSessionId());
    	Principal pripal=request.getUserPrincipal();
    	log.info("UserPrincipal:"+pripal);
    	log.info("x-forwarded-for:"+request.getHeader("x-forwarded-for"));

		List<HowbuyRecommend> list = repo.findAll();
		
		return new ResponseEntity<>( list, null, HttpStatus.OK );
	}
	
	@RequestMapping(value="/open", method=RequestMethod.GET)
	public ResponseEntity<?> queryOpen( ) {
		List<HowbuyRecommend> list = template.find( 
				new Query(new Criteria("status").regex("募集")), 
				HowbuyRecommend.class );
		
		return new ResponseEntity<>(list, null, HttpStatus.OK);
	}
}
