package com.example.jpademo.repository;

import com.example.jpademo.domain.Member;
import com.example.jpademo.domain.Worker;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkerRepository extends ReactiveMongoRepository<Worker,Integer> {
}
