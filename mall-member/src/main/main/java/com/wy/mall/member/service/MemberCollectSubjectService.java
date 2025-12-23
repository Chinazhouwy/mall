package com.wy.mall.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wy.common.utils.PageUtils;
import com.wy.mall.member.entity.MemberCollectSubjectEntity;

import java.util.Map;

/**
 * 会员收藏的专题活动
 *
 * @author zhouwy
 * @email zhou_wy1991@foxmail.com
 * @date 2025-12-21 12:04:15
 */
public interface MemberCollectSubjectService extends IService<MemberCollectSubjectEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

