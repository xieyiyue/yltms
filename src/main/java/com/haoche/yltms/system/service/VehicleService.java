package com.haoche.yltms.system.service;

import com.haoche.yltms.system.model.User;
import com.haoche.yltms.system.model.Vehicle;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface VehicleService {
    Page<Vehicle> findVehicles(Integer page, Integer limit, Map<String, String> params);

    void saveAndUpdate(Vehicle vehicle, User user);

    Vehicle findById(String id);

    void del(String id, String id1);

    List<Vehicle> findVehicles(Map<String, String> params);
}
