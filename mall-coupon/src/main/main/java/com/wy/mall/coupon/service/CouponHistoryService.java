package com.wy.mall.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wy.common.utils.PageUtils;
import com.wy.mall.coupon.entity.CouponHistoryEntity;

import java.util.Map;

/**
 * 优惠券领取历史记录
 *
 * @author zhouwy
 * @email zhou_wy1991@foxmail.com
 * @date 2025-12-21 11:55:55
 */
public interface CouponHistoryService extends IService<CouponHistoryEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

