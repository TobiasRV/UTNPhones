package com.utn.utnphones.service;

import com.utn.utnphones.model.User;
import com.utn.utnphones.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public void addUser(final User u){
        userRepository.save(u);
    }
}
