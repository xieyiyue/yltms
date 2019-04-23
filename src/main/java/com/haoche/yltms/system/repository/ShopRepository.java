package com.haoche.yltms.system.repository;

import com.haoche.yltms.system.model.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ShopRepository extends JpaRepository<Shop, String>, JpaSpecificationExecutor<Shop> {

    @Query(value = "SELECT DISTINCT PROV FROM SHOP WHERE IS_DELETE IS NULL OR IS_DELETE <> 1",nativeQuery = true)
    List<String> findAddress();

    @Query(value = "SELECT DISTINCT CITY FROM SHOP WHERE (IS_DELETE IS NULL OR IS_DELETE <> 1) AND PROV =:prov", nativeQuery =  true)
    List<String> findAddress(@Param("prov") String prov);

    @Query(value = "SELECT DISTINCT  AREA FROM SHOP WHERE (IS_DELETE IS NULL OR IS_DELETE <> 1) AND PROV =:prov AND CITY =:city" , nativeQuery = true)
    List<String> findAddress(@Param("prov") String prov,@Param("city") String city);

    @Query(value = "SELECT DISTINCT  SHOP_NAME FROM SHOP WHERE (IS_DELETE IS NULL OR IS_DELETE <> 1) AND PROV =:prov AND CITY =:city AND AREA =:area" , nativeQuery = true)
    List<String> findAddress(@Param("prov") String prov,@Param("city") String city,@Param("area") String area);


}
