package com.utn.utnphones.service;

import com.utn.utnphones.exceptions.*;
import com.utn.utnphones.model.User;
import com.utn.utnphones.repository.CityRepository;
import com.utn.utnphones.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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


}
