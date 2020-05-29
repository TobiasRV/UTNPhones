package com.utn.utnphones.repository;


import com.utn.utnphones.model.Line;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface LineRepository extends JpaRepository<Line, Integer> {


    // TODO Hacer dto para que devuelva telefono mas llamado y la cantidad de llamadas
    @Query(value = "select * from phone_lines as dp inner join calls as c on (dp.id_line = c.id_destination_line) inner join phone_lines as op on (op.id_line = c.id_origin_line) where (op.id_user = ?1) group by c.id_destination_line order by count(c.id_destination_line) DESC limit 10", nativeQuery = true)
    List<Line> getTop10Destinations(Integer userId);
}
