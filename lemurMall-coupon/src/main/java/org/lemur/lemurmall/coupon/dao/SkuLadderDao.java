package org.lemur.lemurmall.coupon.dao;

import org.lemur.lemurmall.coupon.entity.SkuLadderEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品阶梯价格
 * 
 * @author Lemur
 * @email Lemur@gmail.com
 * @date 2023-02-27 22:38:04
 */
@Mapper
public interface SkuLadderDao extends BaseMapper<SkuLadderEntity> {
	
}
