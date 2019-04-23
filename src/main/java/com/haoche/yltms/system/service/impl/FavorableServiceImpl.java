package com.haoche.yltms.system.service.impl;

import com.haoche.yltms.system.model.Favorable;
import com.haoche.yltms.system.model.User;
import com.haoche.yltms.system.model.Vehicle;
import com.haoche.yltms.system.repository.FavorableRepository;
import com.haoche.yltms.system.repository.VehicleRepository;
import com.haoche.yltms.system.service.FavorableService;
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
import java.util.List;
import java.util.Map;

@Service
public class FavorableServiceImpl implements FavorableService {
    @Autowired
    private FavorableRepository favorableRepository;
    @Autowired
    private VehicleRepository vehicleRepository;

    @Override
    public Favorable findById(String id) {
        return this.favorableRepository.getOne(id);
    }

    @Override
    public Page<Favorable> findFavorables(Integer pageNo, Integer limit, Map<String, String> params) {
        String model = params.get("model");
        Specification<Favorable> specification = (Specification<Favorable>) (root, criteriaQuery, criteriaBuilder) -> {
            Path<String> isDelete = root.get("isDelete");
            Predicate p1 = criteriaBuilder.isNull(isDelete);
            Predicate p3 = criteriaBuilder.notEqual(isDelete,"1");
            Predicate p = criteriaBuilder.or(p1,p3);
            if (!StringUtils.isEmpty(model)) {
                Path<String> licensePath = root.get("vehicle.model");
                Predicate p2 = criteriaBuilder.like(licensePath, "%" + model + "%");
                return criteriaBuilder.and(p, p2);
            }
            return p;
        };
        Sort sort = new Sort(Sort.DEFAULT_DIRECTION, "createTime");
        PageRequest pageable = PageRequest.of(pageNo - 1, limit, sort);
        Page<Favorable> page = this.favorableRepository.findAll(specification, pageable);
        return page;
    }

    @Override
    public void saveAndUpdate(Favorable favorable, User user) {
        Date now = new Date();
        Vehicle vehicle = this.vehicleRepository.getOne(favorable.getVehicleId());
        if(vehicle == null){
            throw new RuntimeException("没有此车辆信息");
        }
        favorable.setVehicle(vehicle);
        if (StringUtils.isEmpty(favorable.getId())) {
            favorable.setId(UUIDGenerator.getUUID());
            favorable.setCreateTime(now);
            favorable.setCreator(user.getId());
            this.favorableRepository.save(favorable);
        } else {
            Favorable old = this.favorableRepository.getOne(favorable.getId());
            favorable.setModifier(user.getId());
            favorable.setModifyTime(now);
            CopyUtils.copyProperties(favorable, old);
            this.favorableRepository.save(old);

        }
    }

    @Override
    public void del(String id, String userId) {
        Date now = new Date();
        Favorable favorable = this.favorableRepository.getOne(id);
        favorable.setIsDelete(Favorable.IS_DETELE);
        favorable.setDeleter(userId);
        favorable.setDeleteTime(now);
        this.favorableRepository.save(favorable);
    }

    @Override
    public List<Favorable> findFavorables(Map<String, String> params) {
        Specification<Favorable> specification = (Specification<Favorable>) (root, criteriaQuery, criteriaBuilder) -> {
            Path<String> isDelete = root.get("isDelete");
            Predicate p1 = criteriaBuilder.isNull(isDelete);
            Predicate p3 = criteriaBuilder.notEqual(isDelete,"1");
            Predicate p = criteriaBuilder.or(p1,p3);
            return p;
        };
        List<Favorable> list = this.favorableRepository.findAll(specification);
        return list;
    }
}
