package org.lemur.lemurmall.product.dao;

import org.apache.ibatis.annotations.Param;
import org.lemur.lemurmall.product.entity.CategoryBrandRelationEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 品牌分类关联
 * 
 * @author Lemur
 * @email Lemur@gmail.com
 * @date 2023-02-27 15:08:47
 */
@Mapper
public interface CategoryBrandRelationDao extends BaseMapper<CategoryBrandRelationEntity> {

    void updateCategory(@Param("catId") Long catId, @Param("name") String name);
}
