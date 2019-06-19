package com.example.jpademo.repository;


import com.example.jpademo.domain.Member;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends ReactiveMongoRepository<Member,Integer> {
}
