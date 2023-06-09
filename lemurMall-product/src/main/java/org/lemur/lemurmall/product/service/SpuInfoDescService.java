package org.lemur.lemurmall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.lemur.common.utils.PageUtils;
import org.lemur.lemurmall.product.entity.SpuInfoDescEntity;

import java.util.Map;

/**
 * spu信息介绍
 *
 * @author Lemur
 * @email Lemur@gmail.com
 * @date 2023-02-27 15:08:47
 */
public interface SpuInfoDescService extends IService<SpuInfoDescEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveSpuInfoDesc(SpuInfoDescEntity spuInfoDescEntity);
}

