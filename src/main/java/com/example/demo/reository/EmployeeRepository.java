package com.example.demo.reository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee,Long> {

	Employee findByEmail(String email);

	Employee findByName(String name);
	
	List<Employee> findByNameContaining(String name);

}
