package com.example.jpademo.repository;


import com.example.jpademo.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member,Integer> {
    public Member findByUserName(String userName);
    public List<Member> findByUserNameAndPassword(String userName,String password);
}
