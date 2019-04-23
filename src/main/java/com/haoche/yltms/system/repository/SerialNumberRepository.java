package com.haoche.yltms.system.repository;

import com.haoche.yltms.system.model.SerialNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SerialNumberRepository extends JpaRepository<SerialNumber, String> {
    @Query(value = "from SerialNumber where serialName = 'orderNo'")
    List<SerialNumber> getOrderNo();
}
