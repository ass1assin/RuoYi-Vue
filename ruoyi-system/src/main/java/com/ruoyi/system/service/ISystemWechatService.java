package com.ruoyi.system.service;

import com.ruoyi.system.domain.SystemComment;
import com.ruoyi.system.domain.SystemOrders;
import com.ruoyi.system.domain.SystemUserAddress;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface ISystemWechatService {
    public int insertSystemUserAddress(SystemUserAddress systemUserAddress);

    public int updateSystemUserAddress(SystemUserAddress systemUserAddress);

    public int deleteSystemUserAddress(Long id);

    public List<SystemUserAddress> getUserAddress(Long userId);

    public List<SystemOrders> getOrder(SystemOrders systemOrders);

    public int createOrder(SystemOrders systemOrders);

    public int cancelOrder(SystemOrders systemOrders);

    public List<SystemComment> getComment(SystemComment systemComment);
}
