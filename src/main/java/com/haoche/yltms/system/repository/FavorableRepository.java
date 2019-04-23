package com.haoche.yltms.system.repository;

import com.haoche.yltms.system.model.Favorable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface FavorableRepository extends JpaRepository<Favorable, String>, JpaSpecificationExecutor<Favorable> {

}
