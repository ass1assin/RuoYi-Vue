package com.ruoyi.payment.mapper;


import com.ruoyi.payment.domain.OrderUpdateParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;
import org.springframework.data.repository.query.Param;

@Mapper
public interface PaymentMapper {

    /**
     * 根据订单号更新订单状态
     * @param orderNo 订单号
     * @param status 订单状态
     * @return 更新记录数
     */
    @Update("UPDATE t_order SET status = #{status}, update_time = NOW() WHERE order_no = #{orderNo}")
    int updateOrderStatusByOrderNo(OrderUpdateParam param);
}
