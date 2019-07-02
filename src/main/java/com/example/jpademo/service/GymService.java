package com.example.jpademo.service;

import com.example.jpademo.domain.Gym;
import com.example.jpademo.repository.GymRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class GymService {

    private GymRepository gymRepository;
    @Autowired
    public void setGymRepository(GymRepository gymRepository){
        this.gymRepository=gymRepository;
    }

    public Flux<Gym> getAllGym(){
        return this.gymRepository.findAll();
    }
}
