package org.lemur.lemurmall.ware.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.lemur.common.utils.PageUtils;
import org.lemur.common.utils.Query;
import org.lemur.common.utils.R;
import org.lemur.lemurmall.ware.dao.WareSkuDao;
import org.lemur.lemurmall.ware.entity.PurchaseDetailEntity;
import org.lemur.lemurmall.ware.entity.WareSkuEntity;
import org.lemur.lemurmall.ware.feign.ProductFeignService;
import org.lemur.lemurmall.ware.service.WareSkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("wareSkuService")
public class WareSkuServiceImpl extends ServiceImpl<WareSkuDao, WareSkuEntity> implements WareSkuService {

    @Autowired
    ProductFeignService productFeignService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {

        String skuId = (String) params.get("skuId");
        String wareId = (String) params.get("wareId");

        LambdaQueryWrapper<WareSkuEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ObjectUtils.isNotEmpty(skuId), WareSkuEntity::getSkuId, skuId).eq(ObjectUtils.isNotEmpty(wareId), WareSkuEntity::getWareId, wareId);
        IPage<WareSkuEntity> page = this.page(
                new Query<WareSkuEntity>().getPage(params),
                wrapper
        );

        return new PageUtils(page);
    }

    @Override
    public void addStock(List<PurchaseDetailEntity> purchaseDetailEntities) {
        for (PurchaseDetailEntity purchaseDetailEntity : purchaseDetailEntities) {
            Long skuId = purchaseDetailEntity.getSkuId();
            Long wareId = purchaseDetailEntity.getWareId();
            Integer skuNum = purchaseDetailEntity.getSkuNum();
            //1.判断如果还没有这个库存记录新增
            List<WareSkuEntity> wareSkuEntities = baseMapper.selectList(new LambdaQueryWrapper<WareSkuEntity>().eq(WareSkuEntity::getSkuId, skuId).eq(WareSkuEntity::getWareId, wareId));
            if (ObjectUtils.isEmpty(wareSkuEntities)) {
                WareSkuEntity wareSkuEntity = new WareSkuEntity();
                wareSkuEntity.setSkuId(skuId);
                wareSkuEntity.setStock(skuNum);
                wareSkuEntity.setWareId(wareId);
                wareSkuEntity.setStockLocked(0);

                try {
                    R info = productFeignService.info(skuId);
                    Map<String, Object> data = (Map<String, Object>) info.get("skuInfo");
                    if (info.getCode() == 0) {
                        wareSkuEntity.setSkuName((String) data.get("skuName"));
                    }
                } catch (Exception e) {

                }

                baseMapper.insert(wareSkuEntity);
            } else {
                baseMapper.addStock(skuId, wareId, skuNum);
            }
        }
    }

}