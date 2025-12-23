package com.wy.mall.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wy.common.utils.PageUtils;
import com.wy.mall.order.entity.OrderReturnApplyEntity;

import java.util.Map;

/**
 * 订单退货申请
 *
 * @author zhouwy
 * @email zhou_wy1991@foxmail.com
 * @date 2025-12-21 12:00:50
 */
public interface OrderReturnApplyService extends IService<OrderReturnApplyEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

