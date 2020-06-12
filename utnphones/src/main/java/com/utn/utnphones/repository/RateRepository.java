package com.utn.utnphones.repository;

import com.utn.utnphones.model.Rate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RateRepository extends JpaRepository<Rate, Integer> {

    @Query(value = "select * from rates as r where (r.id_origin_city = ?1) and (r.id_destination_city = ?2)", nativeQuery = true)
    List<Rate> getRateByCities(Integer fromCityId, Integer toCityId);

    @Query(value = "select * from rates as r where (r.id_origin_city = ?1)", nativeQuery = true)
    List<Rate> getRateByCity(Integer fromCityId);

    @Query(value = "select * from rates as r where (r.id_origin_city = ?1) and (r.id_destination_city = ?2)", nativeQuery = true)
    Rate getOnlyOneRateByCities(Integer fromCityId, Integer toCityId);

    @Query(value = "select case when count(*)> 0 then 'true' else 'false' end from rates where id_origin_city = ?1 and id_destination_city = ?2", nativeQuery = true)
    boolean existsByOriginCityAndDestinationCity(Integer originCity, Integer destinationCity);
}
