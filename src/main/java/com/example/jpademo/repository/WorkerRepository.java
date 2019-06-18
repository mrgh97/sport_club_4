package com.example.jpademo.repository;

import com.example.jpademo.domain.Member;
import com.example.jpademo.domain.Worker;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkerRepository extends JpaRepository<Worker,Integer>{
    @Override
    Page<Worker> findAll(Pageable pageable);
    Worker findWorkerById(Integer id);
}
