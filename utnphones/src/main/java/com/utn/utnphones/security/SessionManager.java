package com.utn.utnphones.security;

import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Hashtable;
import java.util.Map;

@Service
public class SessionManager {

    private static final Map<String, Date> sessionMap = new Hashtable<>();

    int sesionExpiration = 600000;

    public void addSession(String token) {
        sessionMap.put(token, new Date(System.currentTimeMillis() + sesionExpiration));
    }

    public boolean isExpired(String token) {
        Date sessionDate = sessionMap.get(token);
        if (sessionDate == null)
            return true;
        if (sessionDate.before(new Date(System.currentTimeMillis()))) {
            removeSession(token);
            return true;
        }
        return false;
    }

    public void refreshSession(String token) {
        sessionMap.put(token, new Date(System.currentTimeMillis() + sesionExpiration));
    }

    public void removeSession(String token) {
        sessionMap.remove(token);
    }

}
