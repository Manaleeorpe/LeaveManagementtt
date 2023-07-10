package com.example.demo.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.demo.entity.Employee;
import com.example.demo.entity.LeaveBalances;
import com.example.demo.entity.LeaveRequest;
import com.example.demo.reository.EmployeeRepository;
import com.example.demo.reository.LeaveBalanceRepository;
import com.example.demo.reository.LeaveRequestRepository;

@Service
public class LeaveRequestService {

    private final LeaveRequestRepository leaveRequestRepository;
    private final EmployeeRepository employeeRepository;
    private final LeaveBalanceRepository leaveBalanceRepository;
    
    @Autowired
    public LeaveRequestService(LeaveRequestRepository leaveRequestRepository, EmployeeRepository employeeRepository, LeaveBalanceRepository leaveBalanceRepository) {
        this.leaveRequestRepository = leaveRequestRepository;
        this.employeeRepository = employeeRepository;
		this.leaveBalanceRepository = leaveBalanceRepository;
    }
    public List<LeaveRequest> getAllLeaveRequests() {
        return leaveRequestRepository.findAll();
    }
    
    public LeaveRequest createLeaveRequest(@PathVariable Long employeeid, @RequestBody LeaveRequest leaveRequest) {
        Employee employee = employeeRepository.findById(employeeid).orElse(null);
        if (employee != null) {
        	leaveRequest.setEmployeeid(employeeid);
            leaveRequest.setEmployee(employee);
            leaveRequest.setStatus("pending");
            return leaveRequestRepository.save(leaveRequest);
        } else {
            return null;
        }
    }
    
    //leavehistory
    public List<LeaveRequest> getLeaveRequestsByEmployeeId(Long employeeid) {
        return leaveRequestRepository.findByEmployee_employeeid(employeeid);
    }
    
    //leave absenteeism
    public List<LeaveRequest> getLeaveRequestsByEmployeeIdandstatus(Long employeeid) {
        return leaveRequestRepository.findByEmployeeidAndStatus(employeeid , "rejected");
    }
    
    //update the leaverequest status
    public void updatestatus(Long leaveRequestid, String status) {
        LeaveRequest leaveRequest = leaveRequestRepository.findById(leaveRequestid)
                .orElseThrow(() -> new IllegalArgumentException("Leave request not found"));

        String previousStatus = leaveRequest.getStatus();
        leaveRequest.setStatus(status);
        leaveRequestRepository.save(leaveRequest);
        
        if (previousStatus.equals("pending") && status.equals("approved")) {
            updateLeaveBalance(leaveRequest);
        }
    }
    
    private void updateLeaveBalance(LeaveRequest leaveRequest) {
        String leavetype = leaveRequest.getLeavetype();
        Long employeeid = leaveRequest.getEmployee().getEmployeeid();

        LeaveBalances leaveBalance = leaveBalanceRepository.findByEmployee_employeeidAndLeavetype(employeeid, leavetype);
        if (leaveBalance != null) {
            int numberOfDays = leaveRequest.getNumberOfDays(leaveRequest.getStartDate(), leaveRequest.getEndDate());
            leaveBalance.setBalances(leaveBalance.getBalances() - numberOfDays);
            leaveBalanceRepository.save(leaveBalance);
        }
    }
    
    //calendar - for managers
    public List<LeaveRequest> getLeaveRequestsByMonth(int year, int month) {
        LocalDate startMonth = LocalDate.of(year, month, 1);
        LocalDate endMonth = startMonth.withDayOfMonth(startMonth.lengthOfMonth());
        return leaveRequestRepository.findByStartDateBetween(startMonth, endMonth);
    }
    //calendar- for employee
    public List<LeaveRequest> getLeaveRequestsByMonthAndId(int year, int month, Long employeeid) {
        LocalDate startMonth = LocalDate.of(year, month, 1);
        LocalDate endMonth = startMonth.withDayOfMonth(startMonth.lengthOfMonth());
        return leaveRequestRepository.findByStartDateBetweenAndEmployee_employeeid(startMonth, endMonth , employeeid);
    }
    
    
}
