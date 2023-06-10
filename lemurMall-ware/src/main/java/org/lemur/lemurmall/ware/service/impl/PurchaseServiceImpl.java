package org.lemur.lemurmall.ware.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.lemur.common.constant.WareConstant;
import org.lemur.common.utils.PageUtils;
import org.lemur.common.utils.Query;
import org.lemur.lemurmall.ware.dao.PurchaseDao;
import org.lemur.lemurmall.ware.entity.PurchaseDetailEntity;
import org.lemur.lemurmall.ware.entity.PurchaseEntity;
import org.lemur.lemurmall.ware.service.PurchaseDetailService;
import org.lemur.lemurmall.ware.service.PurchaseService;
import org.lemur.lemurmall.ware.service.WareSkuService;
import org.lemur.lemurmall.ware.vo.MergeVo;
import org.lemur.lemurmall.ware.vo.PurchaseDoneVo;
import org.lemur.lemurmall.ware.vo.PurchaseItemDoneVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("purchaseService")
public class PurchaseServiceImpl extends ServiceImpl<PurchaseDao, PurchaseEntity> implements PurchaseService {

    @Autowired
    PurchaseDetailService purchaseDetailService;

    @Autowired
    WareSkuService wareSkuService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<PurchaseEntity> page = this.page(
                new Query<PurchaseEntity>().getPage(params),
                new QueryWrapper<PurchaseEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils queryPageUnreceive(Map<String, Object> params) {

        LambdaQueryWrapper<PurchaseEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(PurchaseEntity::getStatus, 0).or().eq(PurchaseEntity::getStatus, 1);


        IPage<PurchaseEntity> page = this.page(
                new Query<PurchaseEntity>().getPage(params),
                wrapper
        );

        return new PageUtils(page);
    }

    @Transactional
    @Override
    public void mergePurchase(MergeVo mergeVo) {
        Long purchaseId = mergeVo.getPurchaseId();
        if (ObjectUtils.isEmpty(purchaseId)) {
            //1.新建一个
            PurchaseEntity purchaseEntity = new PurchaseEntity();

            purchaseEntity.setStatus(WareConstant.PurchaseStatusEnum.CREATED.getCode());
            purchaseEntity.setCreateTime(new Date());
            purchaseEntity.setUpdateTime(new Date());
            this.save(purchaseEntity);

            purchaseId = purchaseEntity.getId();

        }
        // 2.确认采购单状态是0和1
        List<Long> items = mergeVo.getItems();
        Long finalPurchaseId = purchaseId;

        List<PurchaseDetailEntity> purchaseDetailEntities = purchaseDetailService.listByIds(items);

        List<Long> list = purchaseDetailEntities.stream().filter(purchaseDetailEntity -> purchaseDetailEntity.getStatus() == WareConstant.PurchaseStatusEnum.CREATED.getCode() || purchaseDetailEntity.getStatus() == WareConstant.PurchaseStatusEnum.ASSIGNED.getCode())
                .map(PurchaseDetailEntity::getId).collect(Collectors.toList());

        List<PurchaseDetailEntity> collect = list.stream().map(i -> {
            PurchaseDetailEntity purchaseDetailEntity = new PurchaseDetailEntity();
            purchaseDetailEntity.setId(i);
            purchaseDetailEntity.setPurchaseId(finalPurchaseId);
            purchaseDetailEntity.setStatus(WareConstant.PurchaseStatusEnum.ASSIGNED.getCode());
            return purchaseDetailEntity;
        }).collect(Collectors.toList());


        purchaseDetailService.updateBatchById(collect);

        if (ObjectUtils.isNotEmpty(collect)) {
            PurchaseEntity purchaseEntity = new PurchaseEntity();
            purchaseEntity.setId(purchaseId);
            purchaseEntity.setUpdateTime(new Date());
            this.updateById(purchaseEntity);
        }
    }

    @Override
    public void received(List<Long> ids) {
        //1.确认当前采购单时新建或者已分配状态
        List<PurchaseEntity> collect = ids.stream().map(this::getById)
                .filter(item -> item.getStatus() == WareConstant.PurchaseStatusEnum.CREATED.getCode() || item.getStatus() == WareConstant.PurchaseStatusEnum.ASSIGNED.getCode())
                .peek(item -> {
                    item.setStatus(WareConstant.PurchaseStatusEnum.BUYING.getCode());
                    item.setUpdateTime(new Date());
                })
                .collect(Collectors.toList());

        //2.改变采购单的状态
        this.updateBatchById(collect);


        //3.改变采购项的状态
        List<Long> list = collect.stream().map(PurchaseEntity::getId).collect(Collectors.toList());
        List<PurchaseDetailEntity> purchaseDetailEntities = purchaseDetailService.listDetailByPurchaseIds(list);
        List<PurchaseDetailEntity> detailEntities = purchaseDetailEntities.stream().map(entity -> {
            PurchaseDetailEntity purchaseDetailEntity = new PurchaseDetailEntity();
            purchaseDetailEntity.setId(entity.getId());
            purchaseDetailEntity.setStatus(WareConstant.PurchaseStatusEnum.BUYING.getCode());
            return purchaseDetailEntity;
        }).collect(Collectors.toList());
        purchaseDetailService.updateBatchById(detailEntities);
    }

    @Transactional
    @Override
    public void done(PurchaseDoneVo purchaseDoneVo) {
        Long id = purchaseDoneVo.getId();

        //2、改变采购项的状态
        boolean flag = true;
        List<PurchaseItemDoneVo> items = purchaseDoneVo.getItems();
        List<PurchaseDetailEntity> updates = new ArrayList<>();
        List<Long> itemIds = new ArrayList<>();
        for (PurchaseItemDoneVo item : items) {
            PurchaseDetailEntity purchaseDetailEntity = new PurchaseDetailEntity();
            if (item.getStatus() == WareConstant.PurchaseStatusEnum.HASERROR.getCode()) {
                flag = false;
                purchaseDetailEntity.setStatus(item.getStatus());
            } else {
                purchaseDetailEntity.setStatus(WareConstant.PurchaseStatusEnum.FINISH.getCode());
                Long itemId = item.getItemId();
                itemIds.add(itemId);
            }
            purchaseDetailEntity.setId(item.getItemId());
            updates.add(purchaseDetailEntity);
        }
        purchaseDetailService.updateBatchById(updates);

        //3、将成功采购的进行入库
        List<PurchaseDetailEntity> purchaseDetailEntities = purchaseDetailService.listByIds(itemIds);
        wareSkuService.addStock(purchaseDetailEntities);

        //1、改变采购单状态
        PurchaseEntity purchaseEntity = new PurchaseEntity();
        purchaseEntity.setId(id);
        purchaseEntity.setStatus(flag ? WareConstant.PurchaseStatusEnum.FINISH.getCode() : WareConstant.PurchaseStatusEnum.HASERROR.getCode());
        purchaseEntity.setUpdateTime(new Date());
        this.updateById(purchaseEntity);


    }

}