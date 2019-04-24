package com.haoche.yltms.system.repository;

import com.haoche.yltms.system.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, String>, JpaSpecificationExecutor<User> {

    @Query(value = "from User where username =:username and (isDelete <> 1 or isDelete is null) and type =:type")
    List<User> findByUsername(@Param("username") String username,@Param("type") String type);

    @Query(value = "from User where id =:id")
    User findOne(@Param("id") String id);

}
