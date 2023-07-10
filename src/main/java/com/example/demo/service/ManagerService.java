package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Employee;
import com.example.demo.entity.LeaveBalances;
import com.example.demo.entity.LeaveRequest;
import com.example.demo.entity.Manager;
import com.example.demo.reository.LeaveBalanceRepository;
import com.example.demo.reository.LeaveRequestRepository;
import com.example.demo.reository.ManagerRepository;

@Service
public class ManagerService {
    private final ManagerRepository managerRepository;
    private final LeaveRequestRepository leaveRequestRepository;
    private final LeaveBalanceRepository leaveBalanceRepository;

    public ManagerService(ManagerRepository managerRepository, LeaveRequestRepository leaveRequestRepository, LeaveBalanceRepository leaveBalanceRepository) {
        this.managerRepository = managerRepository;
		this.leaveRequestRepository = leaveRequestRepository;
		this.leaveBalanceRepository = leaveBalanceRepository;
    }

    public List<Manager> getAllManagers() {
        return managerRepository.findAll();
    }

    public Manager getManagerById(Long employeeid) {
        return managerRepository.findById(employeeid).orElse(null);
    }

    public Manager createManager(Manager manager) {
        Manager savedManager = managerRepository.save(manager);
        createLeaveBalances(savedManager.getEmployeeid());

        return savedManager;
    }

    public Manager updateManager(Long employeeid, Manager manager) {
        if (managerRepository.existsById(employeeid)) {
            manager.setManagerid(employeeid);
            return managerRepository.save(manager);
        } else {
            return null;
        }
    }

    public boolean deleteManager(Long employeeid) {
        if (managerRepository.existsById(employeeid)) {
            managerRepository.deleteById(employeeid);
            return true;
        } else {
            return false;
        }
    }
    private void createLeaveBalances(Long employeeid) {
        String[] leaveTypes = {"Maternity Leave", "Paternity Leave", "Personal Leave", "Sick Leave", "Vacation"};
        Integer[] balances = {150, 10, 10, 10, 15};

        for (int i = 0; i < leaveTypes.length; i++) {
            LeaveBalances leaveBalance = new LeaveBalances();
            leaveBalance.setEmployeeid(employeeid);
            leaveBalance.setLeavetype(leaveTypes[i]);
            leaveBalance.setBalances(balances[i]);
            leaveBalanceRepository.save(leaveBalance);
        }
    }
    
   
}
