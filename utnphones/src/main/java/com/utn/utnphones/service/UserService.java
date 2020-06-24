package com.utn.utnphones.service;

import com.utn.utnphones.dto.UpdateUserDto;
import com.utn.utnphones.exceptions.InvalidLoginException;
import com.utn.utnphones.exceptions.UserNotFoundException;
import com.utn.utnphones.exceptions.ValidationException;
import com.utn.utnphones.model.User;
import com.utn.utnphones.model.enums.UserRole;
import com.utn.utnphones.model.enums.UserStatus;
import com.utn.utnphones.repository.UserRepository;
import com.utn.utnphones.security.SessionManager;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.utn.utnphones.security.Constants.SECRET_KEY;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
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

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String newPassword = bCryptPasswordEncoder.encode(u.getPassword());
        u.setPassword(newPassword);

        return userRepository.save(u);
    }

    public User getUserById(Integer userId) throws UserNotFoundException {
        return userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
    }

    public User getUserByUsername(String username) throws UserNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);
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
        if (u.getPassword() != null) {
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            String newPassword = bCryptPasswordEncoder.encode(u.getPassword());
            oldUser.setPassword(newPassword);
        }
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

    public String getJWTToken(Integer userId, String username, UserRole userRole, SessionManager sessionManager) {

        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_" + userRole);

        String token = Jwts
                .builder()
                .setId(userId.toString())
                .setSubject(username)
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .signWith(SignatureAlgorithm.HS512,
                        SECRET_KEY.getBytes()).compact();

        sessionManager.addSession(token);

        return token;
    }

}

