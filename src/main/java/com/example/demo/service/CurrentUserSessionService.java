package com.example.demo.service;

//handle current user sessions
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.CurrentUserSession;
import com.example.demo.reository.CurrentUserSessionRepository;

@Service
public class CurrentUserSessionService {

    private final CurrentUserSessionRepository currentUserSessionRepository;

    @Autowired
    public CurrentUserSessionService(CurrentUserSessionRepository currentUserSessionRepository) {
        this.currentUserSessionRepository = currentUserSessionRepository;
    }

    public CurrentUserSession createCurrentUserSession(CurrentUserSession currentUserSession) {
        return currentUserSessionRepository.save(currentUserSession);
    }

    public CurrentUserSession getCurrentUserSessionById(Integer sessionId) {
        return currentUserSessionRepository.findById(sessionId).orElse(null);
    }

    public CurrentUserSession updateCurrentUserSession(CurrentUserSession currentUserSession) {
        return currentUserSessionRepository.save(currentUserSession);
    }

    public void deleteCurrentUserSession(CurrentUserSession currentUserSession) {
        currentUserSessionRepository.delete(currentUserSession);
    }
    public CurrentUserSession getCurrentUserSessionByRoleAndId(String role, Integer sessionid) {
        return currentUserSessionRepository.findByRoleAndCurrSessionid( role,sessionid);
    }
    public CurrentUserSession findByRoleAndCurrSessionid(String role, Integer sessionid) {
        return currentUserSessionRepository.findByRoleAndCurrSessionid(role, sessionid);
    }
    public CurrentUserSession findByRoleAndPrivateKey(String role, String privateKey) {
        return currentUserSessionRepository.findByRoleAndPrivateKey(role, privateKey);
    }

	public CurrentUserSession findByPrivateKey(String privateKey) {
		return currentUserSessionRepository.findByPrivateKey(privateKey);
	}

}
