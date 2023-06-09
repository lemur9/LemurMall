package org.lemur.lemurmall.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.lemur.common.to.SkuReductionTo;
import org.lemur.common.utils.PageUtils;
import org.lemur.lemurmall.coupon.entity.SkuFullReductionEntity;

import java.util.Map;

/**
 * 商品满减信息
 *
 * @author Lemur
 * @email Lemur@gmail.com
 * @date 2023-02-27 22:38:04
 */
public interface SkuFullReductionService extends IService<SkuFullReductionEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveSkuReduction(SkuReductionTo skuReductionTo);
}

