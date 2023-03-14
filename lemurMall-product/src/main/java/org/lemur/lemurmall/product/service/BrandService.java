package org.lemur.lemurmall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.lemur.common.utils.PageUtils;
import org.lemur.lemurmall.product.entity.BrandEntity;

import java.util.Map;

/**
 * 品牌
 *
 * @author Lemur
 * @email Lemur@gmail.com
 * @date 2023-02-27 15:08:47
 */
public interface BrandService extends IService<BrandEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void updateDetail(BrandEntity brand);
}

