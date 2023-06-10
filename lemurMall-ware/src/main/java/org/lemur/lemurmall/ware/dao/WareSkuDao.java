package org.lemur.lemurmall.ware.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.lemur.lemurmall.ware.entity.WareSkuEntity;

/**
 * 商品库存
 * 
 * @author Lemur
 * @email Lemur@gmail.com
 * @date 2023-02-27 22:59:34
 */
@Mapper
public interface WareSkuDao extends BaseMapper<WareSkuEntity> {

    void addStock(@Param("skuId") Long skuId, @Param("wareId") Long wareId, @Param("skuNum") Integer skuNum);
}
