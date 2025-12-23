package com.wy.mall.member.dao;

import com.wy.mall.member.entity.MemberEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 会员
 * 
 * @author zhouwy
 * @email zhou_wy1991@foxmail.com
 * @date 2025-12-21 12:04:15
 */
@Mapper
public interface MemberDao extends BaseMapper<MemberEntity> {
	
}
