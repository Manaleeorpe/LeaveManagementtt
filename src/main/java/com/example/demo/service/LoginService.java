package com.example.demo.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.CurrentUserSession;
import com.example.demo.entity.Employee;
import com.example.demo.entity.Manager;
import com.example.demo.reository.CurrentUserSessionRepository;
import com.example.demo.reository.EmployeeRepository;
import com.example.demo.reository.ManagerRepository;

import net.bytebuddy.utility.RandomString;

@Service
public class LoginService {
    private final EmployeeRepository employeeRepository;

    private final ManagerRepository managerRepository;

    private final CurrentUserSessionRepository currentUserSessionRepository;

    @Autowired
    public LoginService(EmployeeRepository employeeRepository, ManagerRepository managerRepository,
			CurrentUserSessionRepository currentUserSessionRepository) {
		super();
		this.employeeRepository = employeeRepository;
		this.managerRepository = managerRepository;
		this.currentUserSessionRepository = currentUserSessionRepository;
	}


    public String loginAccount(String email, String password, String role) {
        if (role.equalsIgnoreCase("employee")) {
            Employee employee = employeeRepository.findByEmail(email);
            if (employee == null) {
                return "Invalid email";
            }

            if (employee.getPassword().equals(password)) {
            	String privateKey = RandomString.make(6);
            	CurrentUserSession currentUserSession = new CurrentUserSession();
                currentUserSession.setEmail(email);
                currentUserSession.setRole(role);
                currentUserSession.setPrivateKey(privateKey);
                String PrivateKey = currentUserSession.getPrivateKey();
                currentUserSession.setLoginDateTime(LocalDateTime.now());
                currentUserSessionRepository.save(currentUserSession);
                return PrivateKey;
   
            } else {
                return "Invalid password";
            }
        } else if (role.equalsIgnoreCase("manager")) {
            Manager manager = managerRepository.findByEmail(email);
            if (manager == null) {
                return "Invalid email";
            }

            if (manager.getPassword().equals(password)) {
            	String privateKey = RandomString.make(6);
            	CurrentUserSession currentUserSession = new CurrentUserSession();
                currentUserSession.setEmail(email);
                currentUserSession.setRole(role);
                currentUserSession.setPrivateKey(privateKey);
                currentUserSession.setLoginDateTime(LocalDateTime.now());
                String PrivateKey = currentUserSession.getPrivateKey();
                currentUserSessionRepository.save(currentUserSession);
                return PrivateKey;
            } else {
                return "Invalid password";
            }
        }

        return "Invalid role";
    }
    public String logoutAccount(String email, String password, String role) {
        CurrentUserSession currentUserSession = currentUserSessionRepository.findByEmailAndRole(email, role);
        if (currentUserSession != null) {
            currentUserSessionRepository.delete(currentUserSession);
            return "Logout successful";
        } else {
            return "Invalid session key";
        }
    }

    // Other methods and functionalities...
}

    // Other methods and functionalities...
