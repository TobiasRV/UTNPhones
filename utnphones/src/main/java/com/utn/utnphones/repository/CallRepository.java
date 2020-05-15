package com.utn.utnphones.repository;

import com.utn.utnphones.model.Call;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CallRepository extends JpaRepository<Call, Integer> {
}
