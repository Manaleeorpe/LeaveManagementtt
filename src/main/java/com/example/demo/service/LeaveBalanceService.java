package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.LeaveBalances;
import com.example.demo.entity.LeaveRequest;
import com.example.demo.reository.LeaveBalanceRepository;

@Service
public class LeaveBalanceService {

    private final LeaveBalanceRepository leaveBalanceRepository;

    @Autowired
    public LeaveBalanceService(LeaveBalanceRepository leaveBalanceRepository) {
        this.leaveBalanceRepository = leaveBalanceRepository;
    }

    public List<LeaveBalances> getAllLeaveBalances() {
        return leaveBalanceRepository.findAll();
    }


    //leavebalances
    public List<LeaveBalances> getLeaveBalanceByEmployeeId(Long employeeid) {
        return leaveBalanceRepository.findByEmployee_employeeid(employeeid);
    }
    
}

