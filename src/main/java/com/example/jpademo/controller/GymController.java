package com.example.jpademo.controller;

import com.example.jpademo.controller.util.HeaderUtil;
import com.example.jpademo.controller.util.PaginationUtil;
import com.example.jpademo.domain.Gym;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api
@RestController
@RequestMapping("/api/gym")
public class GymController {

    @ApiOperation("查看体育馆")
    @GetMapping("")
    public ResponseEntity<Gym> getGym(){
        String name = "西体育馆";
        String location = "北京交通大学";
        Gym gym = new Gym(name,location);

        Link selfLink = ControllerLinkBuilder.linkTo(ControllerLinkBuilder
                .methodOn(GymController.class).getGym())
                .withSelfRel();
        gym.add(selfLink);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createAlert("aaa",gym.getName()))
                .body(gym);
    }
}
