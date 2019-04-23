package com.haoche.yltms.system.service;

import com.haoche.yltms.system.model.User;
import org.springframework.data.domain.Page;

import java.util.Map;

public interface UserService {
    void save(User user);

    void changePassword(User user);

    Page<User> findUsers(Integer page, Integer limit, Map<String, String> params);

    void del(String id, String userId);

    User findById(String id);
}
