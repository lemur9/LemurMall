package org.lemur.lemurmall.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.lemur.common.utils.PageUtils;
import org.lemur.lemurmall.ware.entity.PurchaseDetailEntity;
import org.lemur.lemurmall.ware.entity.WareSkuEntity;

import java.util.List;
import java.util.Map;

/**
 * 商品库存
 *
 * @author Lemur
 * @email Lemur@gmail.com
 * @date 2023-02-27 22:59:34
 */
public interface WareSkuService extends IService<WareSkuEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void addStock(List<PurchaseDetailEntity> purchaseDetailEntities);
}

