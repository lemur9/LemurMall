package org.lemur.lemurmall.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.lemur.common.utils.PageUtils;
import org.lemur.common.utils.Query;
import org.lemur.lemurmall.order.dao.PaymentInfoDao;
import org.lemur.lemurmall.order.entity.PaymentInfoEntity;
import org.lemur.lemurmall.order.service.PaymentInfoService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("paymentInfoService")
public class PaymentInfoServiceImpl extends ServiceImpl<PaymentInfoDao, PaymentInfoEntity> implements PaymentInfoService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<PaymentInfoEntity> page = this.page(
                new Query<PaymentInfoEntity>().getPage(params),
                new QueryWrapper<PaymentInfoEntity>()
        );

        return new PageUtils(page);
    }

}