package com.example.jpademo.controller;


import com.example.jpademo.controller.util.HeaderUtil;
import com.example.jpademo.controller.util.PaginationUtil;
import com.example.jpademo.service.MemberService;
import com.example.jpademo.service.WorkerService;
import com.example.jpademo.domain.Member;
import com.example.jpademo.domain.Worker;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.hibernate.jdbc.Work;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Api
@RestController
@RequestMapping("/api/member")
public class MemberController {

    private MemberService memberService;
    private WorkerService workerService;

    @Autowired
    private RedisTemplate<Serializable, Object> redisTemplate;

    @Autowired
    public void setWorkerService(WorkerService workerService) {
        this.workerService = workerService;
    }

    @Autowired
    public void setMemberService(MemberService memberService) {
        this.memberService = memberService;
    }

    private static final Logger log = LoggerFactory.getLogger(MemberController.class);

    @ApiOperation("查看成员列表")
    @ApiImplicitParam(name = "page",value = "页码,每页5个成员")
    @GetMapping("")
    public ResponseEntity<List<Member>> getMemberAndWorkers(@RequestParam(name = "page", defaultValue = "1") int page) {
        log.debug("Get all members.");
        Pageable pageable = new PageRequest(page - 1, 5);
        Page<Member> members;
        CacheControl cacheControl = CacheControl.maxAge(30, TimeUnit.MINUTES);
//        if (redisTemplate.opsForValue().get("members" + "_" + pageable.getPageNumber()) != null) {
//            members = (Page<Member>) redisTemplate.opsForValue().get("members" + "_" + pageable.getPageNumber());
//        } else {
        members = this.memberService.getMembers(pageable);

        Link selfLink = ControllerLinkBuilder.linkTo(ControllerLinkBuilder
        .methodOn(MemberController.class).getMemberAndWorkers(page))
                .withSelfRel();
//            redisTemplate.opsForValue().set("members" + "_" + pageable.getPageNumber(), members);
//        }


        return ResponseEntity.ok()
                .cacheControl(cacheControl)
                .headers(PaginationUtil.generatePaginationHttpHeaders(members, "/api/members"))
                .body(members.getContent());
    }

    @ApiOperation("选择教练")
    @ApiImplicitParam(name = "workerId",value = "教练Id")
    @GetMapping("/select/{workerId}")
    public ResponseEntity<Worker> selectWorker(@PathVariable Integer workerId, HttpServletRequest request) {
        Member member = (Member) request.getSession().getAttribute("user");
        this.memberService.addWorker(member, workerId);
        //this.workerService.addMember(member, workerId);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createAlert("Select a worker successfully!", member.getUserName()))
                .body(this.workerService.getById(workerId));
    }
}
