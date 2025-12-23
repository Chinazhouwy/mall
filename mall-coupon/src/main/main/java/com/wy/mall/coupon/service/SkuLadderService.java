package com.wy.mall.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wy.common.utils.PageUtils;
import com.wy.mall.coupon.entity.SkuLadderEntity;

import java.util.Map;

/**
 * 商品阶梯价格
 *
 * @author zhouwy
 * @email zhou_wy1991@foxmail.com
 * @date 2025-12-21 11:55:55
 */
public interface SkuLadderService extends IService<SkuLadderEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

