package com.example.demo.reository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.LeaveRequest;

public interface LeaveRequestRepository extends JpaRepository<LeaveRequest,Long>  {
	List<LeaveRequest> findByEmployee_employeeid(Long employeeId);
	
	List<LeaveRequest> findByEmployeeidAndStatus(Long employeeId, String status);
	List<LeaveRequest> findByStartDateBetween(LocalDate startMonth, LocalDate endMonth);
	List<LeaveRequest> findByStartDateBetweenAndEmployee_employeeid(LocalDate startMonth, LocalDate endMonth, long employeeid);
}
