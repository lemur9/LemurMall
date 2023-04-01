package org.lemur.lemurmall.product.dao;

import org.apache.ibatis.annotations.Param;
import org.lemur.lemurmall.product.entity.AttrAttrgroupRelationEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 属性&属性分组关联
 * 
 * @author Lemur
 * @email Lemur@gmail.com
 * @date 2023-02-27 15:08:47
 */
@Mapper
public interface AttrAttrgroupRelationDao extends BaseMapper<AttrAttrgroupRelationEntity> {

    void deleteBatchRelation(@Param("entities") List<AttrAttrgroupRelationEntity> entities);
}
