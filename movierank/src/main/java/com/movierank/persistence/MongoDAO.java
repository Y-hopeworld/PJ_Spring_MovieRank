package com.movierank.persistence;

import java.util.List;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.movierank.domain.MovieDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class MongoDAO {

	// MongoTemplate :
	// MongoOperations : sqlsession같은 존재
	// MovieDTO Type 객체를 생성 이름을 자동으로 생성
	// 첫글자를 소문자로 해서 movieDTO 생성 
	// 만약, 이름값을 설정하고 싶으면 movieDTO →  @Document(collection="movie") 설정
	
	
	@Autowired
	private MongoOperations mongoOper;
	
	public void save(MovieDTO mDto) {
		log.info(">>>>>> 영화 랭킹 정보 MongoDB에 저장 ");
		mongoOper.save(mDto);
		// MongoOper.insert () : 기존_id값이 중복 되면 Error
		//  mongoOper.save() : 기존 _id값과 중복 되면 최신 걸로 덮어씌움 
	}
	
	public void dropCol() {
		log.info(">>>>>> Collection Drop");
		mongoOper.dropCollection("movie");
	}
	
	
	public List<MovieDTO> movieList(){
		log.info(">>>>>> 영화 랭킹정보 MongoDB에 저장" );
		// Criteria(key값) 
		// Criteria cri = new Criteria(key);
		// cri.is(value);  → 컬렉션(key)에서 value인 값을 찾아주세요.
		// (== SELECT * FROM WHERE key = value)
		
		Criteria cri = new Criteria();
		Query query = new Query(cri);
		
		// find(select역할)(query 있어야함, resultType,어떤 컬렉션 )
		// moive라는 컬렉션을 query를 한 후 결과를 dto타입으로 해서 list에 저장
		List<MovieDTO> list = mongoOper.find(query, MovieDTO.class, "movie");
		
		for (MovieDTO one : list) {
			log.info(one.toString());
		}
			
		return list;
	}
	
}
