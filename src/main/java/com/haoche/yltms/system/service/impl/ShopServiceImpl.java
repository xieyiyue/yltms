package com.haoche.yltms.system.service.impl;

import com.haoche.yltms.system.model.Shop;
import com.haoche.yltms.system.model.User;
import com.haoche.yltms.system.repository.ShopRepository;
import com.haoche.yltms.system.service.ShopService;
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
public class ShopServiceImpl implements ShopService {
    @Autowired
    private ShopRepository shopRepository;

    @Override
    public Shop findById(String id) {
        return this.shopRepository.getOne(id);
    }

    @Override
    public Page<Shop> findShops(Integer pageNo, Integer limit, Map<String, String> params) {
        String shopName = params.get("shopName");
        Specification<Shop> specification = (Specification<Shop>) (root, criteriaQuery, criteriaBuilder) -> {
            Path<String> isDelete = root.get("isDelete");
            Predicate p1 = criteriaBuilder.isNull(isDelete);
            Predicate p3 = criteriaBuilder.notEqual(isDelete,"1");
            Predicate p = criteriaBuilder.or(p1,p3);
            if (!StringUtils.isEmpty(shopName)) {
                Path<String> shopNamePath = root.get("shopName");
                Predicate p2 = criteriaBuilder.like(shopNamePath, "%" + shopName + "%");
                return criteriaBuilder.and(p, p2);
            }
            return p;
        };
        Sort sort = new Sort(Sort.DEFAULT_DIRECTION, "address");
        PageRequest pageable = PageRequest.of(pageNo - 1, limit, sort);
        Page<Shop> page = this.shopRepository.findAll(specification, pageable);
        return page;
    }

    @Override
    public void saveAndUpdate(Shop shop, User user) {

        Date now = new Date();
        if (StringUtils.isEmpty(shop.getId())) {
            shop.setId(UUIDGenerator.getUUID());
            shop.setCreateTime(now);
            shop.setCreator(user.getId());
            this.shopRepository.save(shop);
        } else {
            Shop old = this.shopRepository.getOne(shop.getId());
            shop.setModifier(user.getId());
            shop.setModifyTime(now);
            CopyUtils.copyProperties(shop, old);
            this.shopRepository.save(old);

        }
    }

    @Override
    public void del(String id, String userId) {
        Date now = new Date();
        Shop shop = this.shopRepository.getOne(id);
        shop.setIsDelete(Shop.IS_DETELE);
        shop.setDeleter(userId);
        shop.setDeleteTime(now);
        this.shopRepository.save(shop);
    }

    @Override
    public List<String> findShops(Map<String, String> params) {
        String prov = params.get("prov");
        String city = params.get("city");
        String area = params.get("area");
        if(!StringUtils.isEmpty(area)){
            return this.shopRepository.findAddress(prov, city, area);
        }
        if(!StringUtils.isEmpty(city)){
            return this.shopRepository.findAddress(prov, city);
        }
        if(!StringUtils.isEmpty(prov)){
            return this.shopRepository.findAddress(prov);
        }
        if(StringUtils.isEmpty(prov)){
            return this.shopRepository.findAddress();
        }
        return null;
    }
}
