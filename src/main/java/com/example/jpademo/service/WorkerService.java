package com.example.jpademo.service;

import com.example.jpademo.domain.Worker;
import com.example.jpademo.repository.WorkerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
public class WorkerService {

	private WorkerRepository workerRepository;
	@Autowired
	public void setWorkerRepository(WorkerRepository w){
		this.workerRepository=w;
	}

	public Flux<Worker> getAllWorkers(){
		return workerRepository.findAll();
	}

	@Transactional
	public Mono<Worker> getById(Integer id) {
		return workerRepository.findById(id);
	}

	@Transactional
	public Mono<Void> deleteWorker(Integer WorkerId) {
		return workerRepository.deleteById(WorkerId);
	}

	@Transactional
	public Mono<Worker> addWorker(Worker Worker) {
		return workerRepository.save(Worker);
	}

	@Transactional
	public Mono<Worker> updateWorker(Worker Worker) {
		return workerRepository.save(Worker);
	}


}
