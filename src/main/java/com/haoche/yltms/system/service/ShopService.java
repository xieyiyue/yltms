package com.haoche.yltms.system.service;

import com.haoche.yltms.system.model.Shop;
import com.haoche.yltms.system.model.User;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface ShopService {
    Shop findById(String id);

    Page<Shop> findShops(Integer page, Integer limit, Map<String, String> params);

    void saveAndUpdate(Shop shop, User user);

    void del(String id, String id1);

    List<String> findShops(Map<String, String> params);
}
