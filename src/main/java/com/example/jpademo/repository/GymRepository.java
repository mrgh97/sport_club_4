package com.example.jpademo.repository;

import com.example.jpademo.domain.Gym;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface GymRepository extends ReactiveMongoRepository<Gym,Integer> {
}
