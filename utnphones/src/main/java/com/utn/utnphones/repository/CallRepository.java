package com.utn.utnphones.repository;

import com.utn.utnphones.model.Call;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface CallRepository extends JpaRepository<Call, Integer> {

    @Query(value = "select * FROM calls as c INNER JOIN phone_lines as p on p.id_line = c.id_origin_Line WHERE (p.id_User = ?1) AND (c.call_date >= ?2) AND (c.call_date <= ?3)", nativeQuery = true)
    List<Call> getCallsByUserAndDate(Integer userId, Date fromDate, Date toDate);



    // TODO Hacer dto para que devuelva telefono mas llamado y la cantidad de llamadas
    @Query(value = "select * from calls as c inner join phone_lines as p on (c.id_origin_line = p.id_line) where (p.id_user = ?1) group by c.id_destination_line, p.id_line, p.id_user order by count(c.id_destination_line) DESC limit 10", nativeQuery = true)
    List<Call> getTop10Destinations(Integer userId);

    @Query(value = "select * FROM calls as c INNER JOIN phone_lines as p on p.id_line = c.id_origin_Line WHERE (p.id_User = ?1)", nativeQuery = true)
    List<Call> getCallsByUser(Integer userId);
}
