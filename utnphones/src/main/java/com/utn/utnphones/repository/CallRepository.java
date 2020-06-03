package com.utn.utnphones.repository;

import com.utn.utnphones.dto.LineAndQtyOfCallsDto;
import com.utn.utnphones.model.Call;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface CallRepository extends JpaRepository<Call, Integer> {

    @Query(value = "select * FROM calls as c INNER JOIN phone_lines as p on p.id_line = c.id_origin_Line WHERE (p.id_User = ?1)", nativeQuery = true)
    List<Call> getCallsByUser(Integer userId);

    @Query(value = "select * FROM calls as c INNER JOIN phone_lines as p on p.id_line = c.id_origin_Line WHERE (p.id_User = ?1) AND (c.call_date >= ?2) AND (c.call_date <= ?3)", nativeQuery = true)
    List<Call> getCallsByUserAndDate(Integer userId, Date fromDate, Date toDate);

    @Query(value = "select * FROM calls as c INNER JOIN phone_lines as p on p.id_line = c.id_origin_Line WHERE (p.id_User = ?1) AND (c.call_price > ?2)", nativeQuery = true)
    List<Call> getCallsByUserOverPrice(Integer userId, Float price);

    @Query(value = "select * from calls order by call_duration desc limit 1",nativeQuery = true)
    Call getLongestCall();
}
