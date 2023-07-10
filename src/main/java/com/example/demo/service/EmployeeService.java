package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.CurrentUserSession;
import com.example.demo.entity.Employee;
import com.example.demo.entity.LeaveBalances;
import com.example.demo.reository.EmployeeRepository;
import com.example.demo.reository.LeaveBalanceRepository;
import com.example.demo.reository.LeaveRequestRepository;

@Service
public class EmployeeService{

    private final EmployeeRepository employeeRepository;
    private final LeaveRequestRepository leaveRequestRepository;
    private final LeaveBalanceRepository leaveBalanceRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository, LeaveRequestRepository leaveRequestRepository, LeaveBalanceRepository leaveBalanceRepository) {
        this.employeeRepository = employeeRepository;
        this.leaveRequestRepository = leaveRequestRepository;
		this.leaveBalanceRepository = leaveBalanceRepository;
    }


    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee getEmployeeById(Long employeeid) {
        return employeeRepository.findById(employeeid).orElse(null);
    }

    public Employee createEmployee(Employee employee) {
    	Employee savedEmployee = employeeRepository.save(employee);
        createLeaveBalances(savedEmployee.getEmployeeid());
        return savedEmployee;
    }

    public Employee updateEmployee(Long employeeid, Employee employee) {
        if (employeeRepository.existsById(employeeid)) {
            employee.setEmployeeid(employeeid);
            return employeeRepository.save(employee);
        } else {
            return null;
        }
    }

    public boolean deleteEmployee(Long employeeid) {
        if (employeeRepository.existsById(employeeid)) {
            employeeRepository.deleteById(employeeid);
            return true;
        } else {
            return false;
        }
    }
    
  //create leave balances
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
    
  //employeeid from email
    public Long findByemailid(String email) {
    	Employee employee = employeeRepository.findByEmail(email);
        return employee.getEmployeeid();
    }
   
    public List<Employee> findEmployeesByName(String name) {
        return employeeRepository.findByNameContaining(name);
    }

}
