package org.lemur.lemurmall.coupon.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.lemur.common.utils.PageUtils;
import org.lemur.common.utils.Query;
import org.lemur.lemurmall.coupon.dao.HomeSubjectDao;
import org.lemur.lemurmall.coupon.entity.HomeSubjectEntity;
import org.lemur.lemurmall.coupon.service.HomeSubjectService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("homeSubjectService")
public class HomeSubjectServiceImpl extends ServiceImpl<HomeSubjectDao, HomeSubjectEntity> implements HomeSubjectService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<HomeSubjectEntity> page = this.page(
                new Query<HomeSubjectEntity>().getPage(params),
                new QueryWrapper<HomeSubjectEntity>()
        );

        return new PageUtils(page);
    }

}