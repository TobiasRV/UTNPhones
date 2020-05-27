package com.utn.utnphones.service;

import com.utn.utnphones.exceptions.UserNotExistsException;
import com.utn.utnphones.exceptions.UserNotFoundException;
import com.utn.utnphones.exceptions.ValidationException;
import com.utn.utnphones.model.User;
import com.utn.utnphones.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User login(String username, String password) throws UserNotExistsException{
        User user = userRepository.getByUsernamePassword(username, password);
        return Optional.ofNullable(user).orElseThrow(() -> new UserNotExistsException());
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User addUser(final User u){
       return userRepository.save(u);
    }

    public User getUserById(Integer userId) throws UserNotFoundException {
        return userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
    }


}
