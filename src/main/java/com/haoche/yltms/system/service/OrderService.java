package com.haoche.yltms.system.service;

import com.haoche.yltms.system.model.RentOrder;
import com.haoche.yltms.system.model.User;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface OrderService {
    void saveAndUpdate(RentOrder order, User user);

    Page<RentOrder> findRentOrders(Integer page, Integer limit, Map<String, String> params);

    List<RentOrder> findOrders(Map<String, String> params);

    void payOrder(String orderId, String userId);

    void cancelOrder(String orderId, String userId);
}
