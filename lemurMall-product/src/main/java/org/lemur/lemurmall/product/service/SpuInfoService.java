package org.lemur.lemurmall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.lemur.common.utils.PageUtils;
import org.lemur.lemurmall.product.entity.SpuInfoEntity;
import org.lemur.lemurmall.product.vo.SpuSaveVo;

import java.util.Map;

/**
 * spu信息
 *
 * @author Lemur
 * @email Lemur@gmail.com
 * @date 2023-02-27 15:08:47
 */
public interface SpuInfoService extends IService<SpuInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveSpnInfo(SpuSaveVo vo);

    PageUtils queryPageByConfition(Map<String, Object> params);
}

