package com.example.demo.reository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Employee;
import com.example.demo.entity.Manager;

public interface ManagerRepository extends JpaRepository<Manager, Long> {

	Manager findByEmail(String email);
}

