package org.lemur.lemurmall.ware.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.lemur.common.utils.PageUtils;
import org.lemur.common.utils.Query;
import org.lemur.lemurmall.ware.dao.PurchaseDetailDao;
import org.lemur.lemurmall.ware.entity.PurchaseDetailEntity;
import org.lemur.lemurmall.ware.service.PurchaseDetailService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("purchaseDetailService")
public class PurchaseDetailServiceImpl extends ServiceImpl<PurchaseDetailDao, PurchaseDetailEntity> implements PurchaseDetailService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {

        LambdaQueryWrapper<PurchaseDetailEntity> queryWrapper = new LambdaQueryWrapper<>();

        String key = (String) params.get("key");
        String status = (String) params.get("status");
        String wareId = (String) params.get("wareId");
        queryWrapper.and(ObjectUtils.isNotEmpty(key), w -> {
                    w.eq(PurchaseDetailEntity::getPurchaseId, key)
                            .or()
                            .eq(PurchaseDetailEntity::getSkuId, key);
                }).eq(ObjectUtils.isNotEmpty(status), PurchaseDetailEntity::getStatus, status)
                .eq(ObjectUtils.isNotEmpty(wareId), PurchaseDetailEntity::getWareId, wareId);

        IPage<PurchaseDetailEntity> page = this.page(
                new Query<PurchaseDetailEntity>().getPage(params),
                queryWrapper
        );

        return new PageUtils(page);
    }

    @Override
    public List<PurchaseDetailEntity> listDetailByPurchaseIds(List<Long> id) {
        return this.list(new LambdaQueryWrapper<PurchaseDetailEntity>().in(PurchaseDetailEntity::getPurchaseId, id));
    }

}