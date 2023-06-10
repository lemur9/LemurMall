package org.lemur.lemurmall.product.vo;

import lombok.Data;
import org.lemur.lemurmall.product.entity.SpuInfoEntity;

@Data
public class SpuInfoEntityVo extends SpuInfoEntity {
    private String catalogName;
    private String brandName;
}
