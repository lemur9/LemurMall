package org.lemur.lemurmall.product.vo;

import lombok.Data;
import org.lemur.lemurmall.product.entity.SkuInfoEntity;

@Data
public class SkuInfoEntityVo extends SkuInfoEntity {
    private String catalogName;
    private String brandName;
}
