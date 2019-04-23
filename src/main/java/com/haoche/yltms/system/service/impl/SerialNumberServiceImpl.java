package com.haoche.yltms.system.service.impl;

import com.haoche.yltms.system.model.SerialNumber;
import com.haoche.yltms.system.repository.SerialNumberRepository;
import com.haoche.yltms.system.service.SerialNumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SerialNumberServiceImpl implements SerialNumberService {
    @Autowired
    private SerialNumberRepository serialNumberRepository;

    @Override
    public String getOrderNo() {
        List<SerialNumber> serialNumbers = this.serialNumberRepository.getOrderNo();
        SerialNumber serialNumber = serialNumbers.get(0);
        String orderNo = serialNumber.getSerialNo();
        String newOrderNo =String.valueOf(Integer.valueOf(orderNo) + 1);
        serialNumber.setSerialNo(newOrderNo);
        this.serialNumberRepository.save(serialNumber);
        return orderNo;
    }
}
