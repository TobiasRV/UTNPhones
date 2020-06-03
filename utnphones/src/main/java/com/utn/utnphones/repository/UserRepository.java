package com.utn.utnphones.repository;

import com.utn.utnphones.dto.ReturnUserDto;
import com.utn.utnphones.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query(value = "select * from users where username = ?1 and password = ?2", nativeQuery = true)
    User getByUsernamePassword(String username, String password);

    @Query(value = "select * from users where id_user = ?1", nativeQuery = true)
    User getById(Integer id);

    boolean existsByUsername(String username);
    boolean existsByDni(Integer dni);

    @Query(value = "select * from users as u where u.dni % 2 = 0", nativeQuery = true)
    List<User> getUserByEvenDni();

    @Query(value = "select * from users as u where u.dni % 2 != 0", nativeQuery = true)
    List<User> getUserByOddDni();


}
