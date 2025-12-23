package com.wy.mall.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wy.common.utils.PageUtils;
import com.wy.mall.member.entity.MemberReceiveAddressEntity;

import java.util.Map;

/**
 * 会员收货地址
 *
 * @author zhouwy
 * @email zhou_wy1991@foxmail.com
 * @date 2025-12-21 12:04:15
 */
public interface MemberReceiveAddressService extends IService<MemberReceiveAddressEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

