package com.haoche.yltms.system.service.impl;

import com.haoche.yltms.system.model.User;
import com.haoche.yltms.system.repository.UserRepository;
import com.haoche.yltms.system.service.UserService;
import com.haoche.yltms.utils.CopyUtils;
import com.haoche.yltms.utils.UUIDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import java.util.Date;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public void save(User user) {
        Date now = new Date();
        if(StringUtils.isEmpty(user.getId())){
            user.setId(UUIDGenerator.getUUID());
            user.setType(User.GENERAL_USER);
            user.setCreateTime(now);
            this.userRepository.save(user);
        }else{
            user.setModifyTime(now);
            User old = this.userRepository.getOne(user.getId());
            CopyUtils.copyProperties(user, old);
            this.userRepository.save(old);
        }
    }

    @Override
    public void changePassword(User user) {
        User old = this.userRepository.getOne(user.getId());
        if (old.getPassword().equals(user.getPasswordO())) {
            CopyUtils.copyProperties(user, old);
            this.userRepository.save(old);
        } else {
            throw new RuntimeException("密码错误");
        }
    }

    @Override
    public Page<User> findUsers(Integer pageNo, Integer limit, Map<String, String> params) {
        String username = params.get("username");
        Specification<User> specification = (Specification<User>) (root, criteriaQuery, criteriaBuilder) -> {
            Path<String> type = root.get("type");
            Path<String> isDelete = root.get("isDelete");
            Predicate p1 = criteriaBuilder.equal(type, User.GENERAL_USER);
            Predicate p3 = criteriaBuilder.isNull(isDelete);
            if (!StringUtils.isEmpty(username)) {
                Path<String> usernamePath = root.get("username");
                Predicate p2 = criteriaBuilder.like(usernamePath, "%" + username + "%");
                Predicate predicate = criteriaBuilder.and(p1, p2, p3);
                return predicate;
            }
            return criteriaBuilder.and(p1, p3);
        };
        Sort sort = new Sort(Sort.DEFAULT_DIRECTION, "username");
        PageRequest pageable = PageRequest.of(pageNo - 1, limit, sort);
        Page<User> page = this.userRepository.findAll(specification, pageable);
        return page;
    }

    @Override
    public void del(String id, String userId) {
        Date now = new Date();
        User user = this.userRepository.getOne(id);
        user.setIsDelete(User.IS_DETELE);
        user.setDeleter(userId);
        user.setDeleteTime(now);
        this.userRepository.save(user);

    }

    @Override
    public User findById(String id) {
        return this.userRepository.findOne(id);
    }

}
