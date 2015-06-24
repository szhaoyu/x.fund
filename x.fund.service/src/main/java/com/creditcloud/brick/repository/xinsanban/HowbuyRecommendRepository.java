package com.creditcloud.brick.repository.xinsanban;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.creditcloud.brick.entity.xinsanban.HowbuyRecommend;

public interface HowbuyRecommendRepository extends MongoRepository<HowbuyRecommend, String> {
	//public List<HowbuyRecommend> findByStatus(String status);
}
