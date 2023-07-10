package com.example.demo.reository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.LeaveBalances;

public interface LeaveBalanceRepository extends JpaRepository<LeaveBalances ,Long>  {

	List<LeaveBalances> findByEmployee_employeeid(Long employeeId);

	LeaveBalances findByEmployee_employeeidAndLeavetype(Long employeeid, String leavetype);
}