package com.example.jpademo.service.impl;

import com.example.jpademo.domain.Member;
import com.example.jpademo.service.MemberService;
import com.example.jpademo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private MemberService memberService;

    public Member getByUsername(String username) {

        return memberService.findMember(username);
    }
}
