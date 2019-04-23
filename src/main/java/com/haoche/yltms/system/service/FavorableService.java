package com.haoche.yltms.system.service;

import com.haoche.yltms.system.model.Favorable;
import com.haoche.yltms.system.model.User;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface FavorableService {
    Favorable findById(String id);

    Page<Favorable> findFavorables(Integer page, Integer limit, Map<String, String> params);

    void saveAndUpdate(Favorable favorable, User user);

    void del(String id, String id1);

    List<Favorable> findFavorables(Map<String, String> params);
}
