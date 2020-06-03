package com.utn.utnphones.service;

import com.utn.utnphones.dto.ReturnUserDto;
import com.utn.utnphones.dto.UpdateUserDto;
import com.utn.utnphones.exceptions.*;
import com.utn.utnphones.model.User;
import com.utn.utnphones.model.enums.UserStatus;
import com.utn.utnphones.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User login(String username, String password) throws UserNotExistsException {
        User user = userRepository.getByUsernamePassword(username, password);
        return Optional.ofNullable(user).orElseThrow(UserNotExistsException::new);
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User addUser(final User u) throws UserAlreadyExistsException {

        if (userRepository.existsByUsername(u.getUsername()) || userRepository.existsByDni(u.getDni()))
            throw new UserAlreadyExistsException();

        return userRepository.save(u);
    }

    public User getUserById(Integer userId) throws UserNotFoundException {
        return userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
    }

    public void deleteUser(Integer userId) throws UserNotExistsException {
        User u = userRepository.findById(userId).orElseThrow(UserNotExistsException::new);
        u.setStatus(UserStatus.DELETED);

        userRepository.save(u);
    }

    public User updateUser(Integer userId, UpdateUserDto u) throws UserNotExistsException, DataIntegrityViolationException {
        User oldUser = userRepository.findById(userId).orElseThrow(UserNotExistsException::new);

        if (u.getUsername() != null)
            oldUser.setUsername(u.getUsername());
        if (u.getPassword() != null)
            oldUser.setPassword(u.getPassword());
        if (u.getName() != null)
            oldUser.setName(u.getName());
        if (u.getLastname() != null)
            oldUser.setLastname(u.getLastname());
        if (u.getDni() != null)
            oldUser.setDni(u.getDni());
        if (u.getCity() != null)
            oldUser.setCity(u.getCity());
        if (u.getRole() != null)
            oldUser.setRole(u.getRole());

        return userRepository.save(oldUser);
    }


    public boolean existsById(Integer userId) {
        return userRepository.existsById(userId);
    }


    public List<User> getUsersByDniEvenOrOdd(Integer condition) throws ParameterNotValidException {

        List<User> response = null;

        if(condition == 0){
           response = userRepository.getUserByEvenDni();
        }else if (condition == 1){
            response =userRepository.getUserByOddDni();
        }else {
            throw new ParameterNotValidException();
        }


        return response;
    }
}

