package com.haoche.yltms.system.service.impl;

import com.haoche.yltms.system.model.User;
import com.haoche.yltms.system.repository.UserRepository;
import com.haoche.yltms.system.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User getUserByUsername(String username, String type) {
        List list = this.userRepository.findByUsername(username, type);
        if(list == null || list.size() == 0){
            return null;
        }
        return (User) list.get(0);
    }
}
