package com.creditcloud.brick.repository.xinsanban;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.creditcloud.brick.entity.xinsanban.PedataRecommend;

public interface PedataRecommandRepository extends MongoRepository<PedataRecommend, String> {
	public List<PedataRecommend> findByStatus(String status);
}
