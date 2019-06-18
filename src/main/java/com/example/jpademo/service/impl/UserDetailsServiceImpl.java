package com.example.jpademo.service.impl;

import com.example.jpademo.domain.Member;
import com.example.jpademo.service.UserService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;


public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Member member = userService.getByUsername(username);
        List<GrantedAuthority> grantedAuthorities = Lists.newArrayList();

        if(member!=null){
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(member.getSign());
            grantedAuthorities.add(grantedAuthority);
        }

        return new User(member.getUserName(),member.getPassword(),grantedAuthorities);
    }
}
