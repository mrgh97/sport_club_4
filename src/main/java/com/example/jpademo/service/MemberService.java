package com.example.jpademo.service;

import com.example.jpademo.domain.Member;
import com.example.jpademo.domain.Worker;
import com.example.jpademo.repository.MemberRepository;
import com.example.jpademo.repository.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MemberService {

    private MemberRepository mRepository;
    private WorkerRepository workerRepository;

    @Autowired
    public void setmRepository(MemberRepository m) {
        this.mRepository = m;
    }

    @Autowired
    public void setWorkerRepository(WorkerRepository workerRepository) {
        this.workerRepository = workerRepository;
    }

    @Transactional
    public void addMember(Member member) {
        mRepository.save(member);
    }

    @Transactional
    public boolean updateMember(Member member) {
        return mRepository.save(member) != null;
    }

    @Transactional
    public void deleteMember(Integer id) {
        mRepository.deleteById(id);
    }

    @Transactional
    public Member findMember(String userName) {
        return mRepository.findByUserName(userName);
    }

    @Transactional
    public boolean checkLogin(String userName, String password) {
        return mRepository.findByUserNameAndPassword(userName, password).size() > 0;
    }

    public void addWorker(Member member, Integer workerId) {
        List<Worker> workers = member.getWorkers();
        workers.add(this.workerRepository.findWorkerById(workerId));
        member.setWorkers(workers);
        this.updateMember(member);
    }

    public Page<Member> getMembers(Pageable pageable) {
        return this.mRepository.findAll(pageable);
    }

    public int getMembersNumber() {
        return this.mRepository.findAll().size();
    }
}
