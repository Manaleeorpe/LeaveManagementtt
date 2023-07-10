package com.example.demo.reository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.CurrentUserSession;
import com.example.demo.entity.Employee;

public interface CurrentUserSessionRepository extends JpaRepository<CurrentUserSession ,Integer>{

	CurrentUserSession findByEmailAndRole(String email, String role);

	CurrentUserSession findByEmail(String email);

	CurrentUserSession findByRoleAndCurrSessionid(String role, Integer sessionid);
	CurrentUserSession findByRoleAndPrivateKey(String role, String privateKey);

	CurrentUserSession findByPrivateKey(String privateKey);

}
