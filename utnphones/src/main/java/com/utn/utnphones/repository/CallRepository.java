package com.utn.utnphones.repository;

import com.utn.utnphones.model.Call;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface CallRepository extends JpaRepository<Call, Integer> {

    //select * FROM calls c INNER JOIN phone_lines p on p.id_Line = c.id_origin_Line WHERE (p.id_User = 41) AND (DATE(call_date) >= "2020-5-24" AND DATE(call_date) <= "2020-5-29");
    @Query(value = "select * FROM calls as c INNER JOIN phone_lines as p on p.id_line = c.id_origin_Line WHERE (p.id_User = ?1) AND (c.call_date >= ?2) AND (c.call_date <= ?3)", nativeQuery = true)
    List<Call> getCallsByUserAndDate(Integer userId, Date fromDate, Date toDate);
}
