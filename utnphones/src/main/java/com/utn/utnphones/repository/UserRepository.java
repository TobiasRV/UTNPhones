package com.utn.utnphones.repository;

import com.utn.utnphones.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query(value = "select * from users where username = ?1 and password = ?2", nativeQuery = true)
    User getByUsernamePassword(String username, String password);
}
