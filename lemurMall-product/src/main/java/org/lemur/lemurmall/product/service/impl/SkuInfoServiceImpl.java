package org.lemur.lemurmall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.lemur.common.exception.LemurMallException;
import org.lemur.common.utils.PageUtils;
import org.lemur.common.utils.Query;
import org.lemur.lemurmall.product.dao.CategoryBrandRelationDao;
import org.lemur.lemurmall.product.dao.SkuInfoDao;
import org.lemur.lemurmall.product.entity.CategoryBrandRelationEntity;
import org.lemur.lemurmall.product.entity.SkuInfoEntity;
import org.lemur.lemurmall.product.service.SkuInfoService;
import org.lemur.lemurmall.product.vo.SkuInfoEntityVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service("skuInfoService")
public class SkuInfoServiceImpl extends ServiceImpl<SkuInfoDao, SkuInfoEntity> implements SkuInfoService {

    @Autowired
    CategoryBrandRelationDao categoryBrandRelationDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SkuInfoEntity> page = this.page(
                new Query<SkuInfoEntity>().getPage(params),
                new QueryWrapper<SkuInfoEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void saveSkuInfo(SkuInfoEntity skuInfoEntity) {
        baseMapper.insert(skuInfoEntity);
    }

    @Override
    public PageUtils queryPageByCondition(Map<String, Object> params) {
        LambdaQueryWrapper<SkuInfoEntity> wrapper = new LambdaQueryWrapper<>();

        String key = (String) params.get("key");
        wrapper.and(ObjectUtils.isNotEmpty(key), w -> {
            w.eq(SkuInfoEntity::getSkuId, key).or().like(SkuInfoEntity::getSkuName, key);
        });


        String max = (String) params.get("max");

        try {
            wrapper.lt(ObjectUtils.isNotEmpty(max) && new BigDecimal(max).compareTo(BigDecimal.ZERO) > 0, SkuInfoEntity::getPrice, max);
        } catch (Exception e) {
            throw new LemurMallException("数字转换异常");
        }

        String min = (String) params.get("min");
        wrapper.ge(ObjectUtils.isNotEmpty(min), SkuInfoEntity::getPrice, min);


        String brandId = (String) params.get("brandId");
        wrapper.eq(ObjectUtils.isNotEmpty(brandId) && !"0".equalsIgnoreCase(brandId), SkuInfoEntity::getBrandId, brandId);

        String catelogId = (String) params.get("catelogId");
        wrapper.eq(ObjectUtils.isNotEmpty(catelogId) && !"0".equalsIgnoreCase(catelogId), SkuInfoEntity::getCatalogId, catelogId);

        IPage<SkuInfoEntity> page = this.page(
                new Query<SkuInfoEntity>().getPage(params), wrapper
        );

        List<SkuInfoEntity> records = page.getRecords();

        List<SkuInfoEntityVo> list = new ArrayList<>();
        for (SkuInfoEntity record : records) {
            LambdaQueryWrapper<CategoryBrandRelationEntity> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(CategoryBrandRelationEntity::getBrandId, record.getBrandId()).eq(CategoryBrandRelationEntity::getCatelogId, record.getCatalogId());
            CategoryBrandRelationEntity categoryBrandRelationEntity = categoryBrandRelationDao.selectOne(queryWrapper);
            SkuInfoEntityVo skuInfoEntityVo = new SkuInfoEntityVo();
            BeanUtils.copyProperties(record, skuInfoEntityVo);
            skuInfoEntityVo.setBrandName(categoryBrandRelationEntity.getBrandName());
            skuInfoEntityVo.setCatalogName(categoryBrandRelationEntity.getCatelogName());
            list.add(skuInfoEntityVo);
        }

        return new PageUtils(page);
    }

}