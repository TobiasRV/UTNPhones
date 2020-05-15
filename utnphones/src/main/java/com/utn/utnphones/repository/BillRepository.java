package com.utn.utnphones.repository;

import com.utn.utnphones.model.Bill;
import com.utn.utnphones.model.Call;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BillRepository extends JpaRepository<Bill, Integer> {

}
