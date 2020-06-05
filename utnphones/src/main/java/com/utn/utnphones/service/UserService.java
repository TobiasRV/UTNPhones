package com.utn.utnphones.service;

import com.utn.utnphones.dto.UpdateUserDto;
import com.utn.utnphones.exceptions.UserNotFoundException;
import com.utn.utnphones.exceptions.ValidationException;
import com.utn.utnphones.model.User;
import com.utn.utnphones.model.enums.UserStatus;
import com.utn.utnphones.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User login(String username, String password) throws UserNotFoundException {
        User user = userRepository.getByUsernamePassword(username, password);
        return Optional.ofNullable(user).orElseThrow(UserNotFoundException::new);
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User addUser(final User u) throws ValidationException {

        if (userRepository.existsByUsername(u.getUsername()))
            throw new ValidationException("Username already exists");
        if (userRepository.existsByDni(u.getDni()))
            throw new ValidationException("DNI already exists");
        if (userRepository.existsByEmail(u.getEmail()))
            throw new ValidationException("Email already exists");

        return userRepository.save(u);
    }

    public User getUserById(Integer userId) throws UserNotFoundException {
        return userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
    }

    public void deleteUser(Integer userId) throws UserNotFoundException {
        User u = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        u.setStatus(UserStatus.DELETED);

        userRepository.save(u);
    }

    public void updateUser(Integer userId, UpdateUserDto u) throws UserNotFoundException, DataIntegrityViolationException {
        User oldUser = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

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
        if (u.getStatus() != null)
            oldUser.setStatus(u.getStatus());

        userRepository.save(oldUser);
    }

    public boolean existsById(Integer userId) {
        return userRepository.existsById(userId);
    }

}

