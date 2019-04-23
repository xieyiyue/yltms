package com.haoche.yltms.system.service.impl;

import com.haoche.yltms.system.model.User;
import com.haoche.yltms.system.model.Vehicle;
import com.haoche.yltms.system.repository.VehicleRepository;
import com.haoche.yltms.system.service.VehicleService;
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
public class VehicleServiceImpl implements VehicleService {
    @Autowired
    private VehicleRepository vehicleRepository;

    @Override
    public Page<Vehicle> findVehicles(Integer pageNo, Integer limit, Map<String, String> params) {
        String license = params.get("license");
        String type = params.get("type");
        Specification<Vehicle> specification = (Specification<Vehicle>) (root, criteriaQuery, criteriaBuilder) -> {
            Path<String> isDelete = root.get("isDelete");
            Predicate p1 = criteriaBuilder.isNull(isDelete);
            Predicate p3 = criteriaBuilder.notEqual(isDelete,"1");
            Predicate p = criteriaBuilder.or(p1,p3);
            if (!StringUtils.isEmpty(license)) {
                Path<String> licensePath = root.get("license");
                Predicate p2 = criteriaBuilder.like(licensePath, "%" + license + "%");
                return criteriaBuilder.and(p, p2);
            }
            if (!StringUtils.isEmpty(type)) {
                Path<String> typePath = root.get("type");
                Predicate p2 = criteriaBuilder.equal(typePath, typePath);
                return criteriaBuilder.and(p, p2);
            }
            return p;
        };
        Sort sort = new Sort(Sort.DEFAULT_DIRECTION, "license");
        PageRequest pageable = PageRequest.of(pageNo - 1, limit, sort);
        Page<Vehicle> page = this.vehicleRepository.findAll(specification, pageable);
        return page;
    }

    @Override
    public void saveAndUpdate(Vehicle vehicle, User user) {
        Date now = new Date();
        if (StringUtils.isEmpty(vehicle.getId())) {
            vehicle.setId(UUIDGenerator.getUUID());
            vehicle.setCreateTime(now);
            vehicle.setCreator(user.getId());
            this.vehicleRepository.save(vehicle);
        } else {
            Vehicle old = this.vehicleRepository.getOne(vehicle.getId());
            vehicle.setModifier(user.getId());
            vehicle.setModifyTime(now);
            CopyUtils.copyProperties(vehicle, old);
            this.vehicleRepository.save(old);

        }
    }

    @Override
    public Vehicle findById(String id) {
        return this.vehicleRepository.getOne(id);
    }

    @Override
    public void del(String id, String userId) {
        Date now = new Date();
        Vehicle vehicle = this.vehicleRepository.getOne(id);
        vehicle.setIsDelete(Vehicle.IS_DETELE);
        vehicle.setDeleter(userId);
        vehicle.setDeleteTime(now);
        this.vehicleRepository.save(vehicle);
    }

    @Override
    public List<Vehicle> findVehicles(Map<String, String> params) {
        String type = params.get("type");
        Specification<Vehicle> specification = (Specification<Vehicle>) (root, criteriaQuery, criteriaBuilder) -> {
            Path<String> isDelete = root.get("isDelete");
            Predicate p1 = criteriaBuilder.isNull(isDelete);
            Predicate p2 = criteriaBuilder.notEqual(isDelete,"1");
            Predicate p = criteriaBuilder.or(p1,p2);
            if(!StringUtils.isEmpty(type)){
                Path<String> typePath = root.get("type");
                return criteriaBuilder.and(criteriaBuilder.equal(typePath,type),p);
            }
            return p;
        };
        return this.vehicleRepository.findAll(specification);
    }
}
