package com.utn.utnphones.service;

import com.utn.utnphones.dto.UpdateUserDto;
import com.utn.utnphones.exceptions.*;
import com.utn.utnphones.model.User;
import com.utn.utnphones.model.enums.UserStatus;
import com.utn.utnphones.repository.CityRepository;
import com.utn.utnphones.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final CityRepository cityRepository;

    @Autowired
    public UserService(UserRepository userRepository, CityRepository cityRepository) {
        this.userRepository = userRepository;
        this.cityRepository = cityRepository;
    }

    public User login(String username, String password) throws UserNotExistsException {
        User user = userRepository.getByUsernamePassword(username, password);
        return Optional.ofNullable(user).orElseThrow(UserNotExistsException::new);
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User addUser(final User u) throws CityNotExistsException, UserAlreadyExistsException {
        if (!cityRepository.existsById(u.getCity().getIdCity()))
            throw new CityNotExistsException();

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

//    public User updateUser(Integer userId, User u) throws UserNotExistsException, JsonConflictException, DataIntegrityViolationException {
//        if (userRepository.existsById(userId)) {
//            // We set the user id manually to avoid receiving null in the userId field
//            if (u.getIdUser() == null) {
//                u.setIdUser(userId);
//            } else if ((u.getIdUser() != userId)) {
//                throw new JsonConflictException();
//            }
//            return userRepository.save(u);
//        } else
//            throw new UserNotExistsException();
//    }


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
}

