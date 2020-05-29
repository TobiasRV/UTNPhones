package com.utn.utnphones.repository;

import com.utn.utnphones.model.City;
import com.utn.utnphones.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City, Integer> {
}
