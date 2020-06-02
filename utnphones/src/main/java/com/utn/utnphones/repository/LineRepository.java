package com.utn.utnphones.repository;


import com.utn.utnphones.dto.LineAndQtyOfCallsDto;
import com.utn.utnphones.model.Line;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface LineRepository extends JpaRepository<Line, Integer> {


    @Query(value = "select * from phone_lines as dp inner join calls as c on (dp.id_line = c.id_destination_line) inner join phone_lines as op on (op.id_line = c.id_origin_line) where (op.id_user = ?1) group by c.id_destination_line order by count(c.id_destination_line) DESC limit 10", nativeQuery = true)
    List<Line> getTop10Destinations(Integer userId);


    @Query(value = "select count(c.id_call) from phone_lines as dp inner join calls as c on (dp.id_line = c.id_destination_line) inner join phone_lines as op on (op.id_line = c.id_origin_line) where (op.id_user = ?1) and (dp.id_line = ?2)", nativeQuery = true)
    Integer getQtyOfCallsToLine(Integer userId,Integer lineId);

    /*
    @Query(value = "select count(c.id_call) as qtyOfCalls from phone_lines as dp inner join calls as c on (dp.id_line = c.id_destination_line) inner join phone_lines as op on (op.id_line = c.id_origin_line) where (op.id_user = ?1) group by c.id_destination_line order by count(c.id_destination_line) DESC limit 10", nativeQuery = true)
    List<LineAndQtyOfCallsDto> getTop10Destinations(Integer userId);
     */

}
