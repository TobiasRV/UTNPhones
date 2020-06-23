package com.utn.utnphones.repository;

import com.utn.utnphones.model.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface BillRepository extends JpaRepository<Bill, Integer> {

    @Query(value = "select * from bills as b inner join phone_lines as pl on pl.id_line = b.id_line where (pl.id_user = ?1) and (b.issue_date >= ?2 ) and (b.issue_date <= ?3)", nativeQuery = true)
    List<Bill> getBillsByUserAndDate(Integer userId, Date fromDate, Date toDate);

    @Query(value = "select * from bills as b inner join phone_lines as pl on pl.id_line = b.id_line where (pl.id_user = ?1)", nativeQuery = true)
    List<Bill> getBillsByUser(Integer userId);
}
