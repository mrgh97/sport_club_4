package com.example.jpademo.service;

import com.example.jpademo.domain.Member;

public interface UserService {
    Member getByUsername(String username);
}
