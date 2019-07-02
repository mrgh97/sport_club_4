package com.example.jpademo.controller;

import com.example.jpademo.domain.Gym;
import com.example.jpademo.service.GymService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Api
@RestController
@RequestMapping("/api/gym")
public class GymController {

    @Autowired
    private GymService gymService;

    @Autowired
    public void setGymService(GymService gymService){
        this.gymService=gymService;
    }

    @ApiOperation("查看体育馆")
    @GetMapping("")
    public Flux<Gym> getGyms(){
        return gymService.getAllGym();
    }
}
