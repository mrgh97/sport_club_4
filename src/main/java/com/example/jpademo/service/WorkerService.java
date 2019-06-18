package com.example.jpademo.service;

import java.util.List;

import com.example.jpademo.domain.Member;
import com.example.jpademo.domain.Worker;
import com.example.jpademo.repository.WorkerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class WorkerService {

	private WorkerRepository workerRepository;
	@Autowired
	public void setWorkerRepository(WorkerRepository w){
		this.workerRepository=w;
	}

	@Transactional
	public Worker getById(Integer id) {
		return workerRepository.getOne(id);
	}

	@Transactional
	public void deleteWorker(Integer WorkerId) {
		workerRepository.deleteById(WorkerId);
	}

	@Transactional
	public void addWorker(Worker Worker) {
		workerRepository.save(Worker);
	}

	@Transactional
	public boolean updateWorker(Worker Worker) {
		return workerRepository.save(Worker) != null;
	}

	@Transactional
	public Page<Worker> getAllWorkers(Integer pageNum){
		Sort sort = new Sort(Sort.Direction.ASC,"id");
		Pageable pageable=new PageRequest(pageNum,2,sort);

		return this.workerRepository.findAll(pageable);
	}

	@Transactional
	public Page<Worker> getAll(Pageable pageable) {
		return this.workerRepository.findAll(pageable);
	}

	public void addMember(Member member,Integer workerId){
		Worker worker = workerRepository.findWorkerById(workerId);
		List<Member> memberList=worker.getMembers();
		memberList.add(member);
		worker.setMembers(memberList);
	}

}
