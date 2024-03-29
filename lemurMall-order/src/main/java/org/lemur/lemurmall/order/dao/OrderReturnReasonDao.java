package org.lemur.lemurmall.order.dao;

import org.lemur.lemurmall.order.entity.OrderReturnReasonEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 退货原因
 * 
 * @author Lemur
 * @email Lemur@gmail.com
 * @date 2023-02-27 22:56:20
 */
@Mapper
public interface OrderReturnReasonDao extends BaseMapper<OrderReturnReasonEntity> {
	
}
