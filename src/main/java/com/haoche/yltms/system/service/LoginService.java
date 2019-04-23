package com.haoche.yltms.system.service;

import com.haoche.yltms.system.model.User;

public interface LoginService {
    public User getUserByUsername(String username, String type);
}
