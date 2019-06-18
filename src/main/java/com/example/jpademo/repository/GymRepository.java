package com.example.jpademo.repository;

import com.example.jpademo.domain.Gym;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GymRepository extends JpaRepository<Gym,Integer> {
}
