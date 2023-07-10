package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.CurrentUserSession;
import com.example.demo.entity.Employee;
import com.example.demo.entity.LoginRequest;
import com.example.demo.service.CurrentUserSessionService;
import com.example.demo.service.EmployeeService;
import com.example.demo.service.LeaveRequestService;
import com.example.demo.service.LoginService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final LeaveRequestService leaveRequestService;
    private final LoginService loginService;
    private final CurrentUserSessionService currentUserSessionService;

    @Autowired
    public EmployeeController(EmployeeService employeeService, LeaveRequestService leaveRequestService, LoginService loginService, CurrentUserSessionService currentUserSessionService) {
        this.employeeService = employeeService;
        this.leaveRequestService = leaveRequestService;
		this.loginService = loginService;
		this.currentUserSessionService = currentUserSessionService;
    }

    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees( @RequestParam(required = false) String PrivateKey) {
    	if (validatePrivateKeyNoRole(PrivateKey)){
    		List<Employee> employees = employeeService.getAllEmployees();
            return ResponseEntity.ok(employees);
    	} else {
    		 return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    	}
    	
    }

    @GetMapping("/{employeeid}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long employeeid, @RequestParam(required = false) String PrivateKey) {
    	if (validatePrivateKeyNoRole(PrivateKey)){
    		Employee employee = employeeService.getEmployeeById(employeeid);
            if (employee != null) {
                return ResponseEntity.ok(employee);
            } else {
                return ResponseEntity.notFound().build();
            }
    	} else {
    		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    	}
    	
    }

    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        Employee createdEmployee = employeeService.createEmployee(employee);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEmployee);
        
    }

    @PutMapping("/{employeeid}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long employeeid, @RequestBody Employee employee, @RequestParam(required = false) String PrivateKey ) {
    	if (validatePrivateKey(PrivateKey, "manager")) {
    		Employee updatedEmployee = employeeService.updateEmployee(employeeid, employee);
            if (updatedEmployee != null) {
                return ResponseEntity.ok(updatedEmployee);
            } else {
                return ResponseEntity.notFound().build();
            }
    	} else {
    		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    	}
		
    }

    @DeleteMapping("/{employeeid}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long employeeid, @RequestParam(required = false) String PrivateKey) {
    	if (validatePrivateKeyNoRole(PrivateKey)){
    		boolean deleted = employeeService.deleteEmployee(employeeid);
            if (deleted) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.notFound().build();
            }
    	} else {
    		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    	}
    	
    }
    
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();
        String token = loginService.loginAccount(email, password, "employee");
        if (token.startsWith("Invalid")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(token);
        } else {
            return ResponseEntity.ok(token);
        }
    }
    @DeleteMapping("/logout")
    public ResponseEntity<String> logout(@RequestParam(required = false) String PrivateKey) {
        if (validatePrivateKeyNoRole(PrivateKey)){
            CurrentUserSession currentUserSession = currentUserSessionService.findByPrivateKey(PrivateKey);
            if (currentUserSession != null) {
                currentUserSessionService.deleteCurrentUserSession(currentUserSession);
                return ResponseEntity.ok("Logged out");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to logout");
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Logout failed");
        }
    }
    
    //use this in every method
    public boolean validatePrivateKey( String PrivateKey, String role) {
    	 if (PrivateKey == null) {
    	        return false; // Or handle the null case according to your requirements
    	    }
        CurrentUserSession currentUserSession = currentUserSessionService.findByRoleAndPrivateKey(role, PrivateKey);
        boolean isOk = false;
        if(currentUserSession != null && currentUserSession.getPrivateKey().equals(PrivateKey) && currentUserSession.getRole().equals(role)) {
        	isOk = true;
        }
        return isOk;
    }
    public boolean validatePrivateKeyNoRole(String PrivateKey) {
   	 if (PrivateKey == null) {
   	        return false; // Or handle the null case according to your requirements
   	    }
       CurrentUserSession currentUserSession = currentUserSessionService.findByPrivateKey(PrivateKey);
       boolean isOk = false;
       if(currentUserSession != null && currentUserSession.getPrivateKey().equals(PrivateKey)) {
       	isOk = true;
       }
       return isOk;
   }
    
    //Gets employeeid from email
    @GetMapping("/email/{email}")
    public ResponseEntity<Long> getEmployeeByEmail(@PathVariable String email) {
        Long employeeid = employeeService.findByemailid(email);
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeid);
        
    }
    
    //get all by name
    @GetMapping("/name")
    public List<Employee> findEmployeesByNameContaining(@RequestParam String name) {
        return employeeService.findEmployeesByName(name);
    }
    

}
