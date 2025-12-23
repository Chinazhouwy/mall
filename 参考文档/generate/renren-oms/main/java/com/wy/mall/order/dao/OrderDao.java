package com.wy.mall.order.dao;

import com.wy.mall.order.entity.OrderEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单
 * 
 * @author zhouwy
 * @email zhou_wy1991@foxmail.com
 * @date 2025-12-21 12:00:50
 */
@Mapper
public interface OrderDao extends BaseMapper<OrderEntity> {
	
}
