package com.example.jpademo.service;

import com.example.jpademo.domain.Member;
import com.example.jpademo.repository.MemberRepository;
import com.example.jpademo.repository.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class MemberService {

    private MemberRepository mRepository;

    @Autowired
    public void setmRepository(MemberRepository m) {
        this.mRepository = m;
    }

    @Transactional
    public Mono<Member> addMember(Member member) {
        return mRepository.save(member);
    }

    @Transactional
    public Mono<Member> updateMember(Member member) {
        return mRepository.save(member);
    }

    @Transactional
    public Mono<Void> deleteMember(Integer id) {
        return mRepository.deleteById(id);
    }

    public Flux<Member> getAllMembers(){
        return mRepository.findAll();
    }

}
