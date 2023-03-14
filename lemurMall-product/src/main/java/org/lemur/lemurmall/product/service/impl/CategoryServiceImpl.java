package org.lemur.lemurmall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.lemur.common.utils.PageUtils;
import org.lemur.common.utils.Query;
import org.lemur.lemurmall.product.dao.CategoryDao;
import org.lemur.lemurmall.product.entity.CategoryEntity;
import org.lemur.lemurmall.product.service.CategoryBrandRelationService;
import org.lemur.lemurmall.product.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

    @Autowired
    CategoryBrandRelationService categoryBrandRelationService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<CategoryEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<CategoryEntity> listWithTree() {
        //1.查询出所有分类
        List<CategoryEntity> entities = baseMapper.selectList(null);

        //2.组装成父子的树形结构
        List<CategoryEntity> level1Menus = entities.stream()
                .filter(categoryEntity -> categoryEntity.getParentCid() == 0)
                .peek(menu -> menu.setChildren(getChildCategories(menu, entities)))
                .sorted(Comparator.comparingInt(menu -> Objects.isNull(menu.getSort()) ? 0 : menu.getSort()))
                .collect(Collectors.toList());

        return level1Menus;
    }

    @Override
    public void removeMenuByIds(List<Long> asList) {

        //TODO 1.检查当前删除的菜单，是否被其他地方引用
        baseMapper.deleteBatchIds(asList);
    }

    @Override
    public Long[] findCatelogPath(Long catelogId) {
        List<Long> paths = new ArrayList<>();
        findParentPath(catelogId, paths);
        Collections.reverse(paths);

        return paths.toArray(new Long[0]);
    }

    @Override
    public void updateDetail(CategoryEntity category) {
        this.updateById(category);
        if (StringUtils.hasLength(category.getName())) {
            categoryBrandRelationService.updateCategory(category.getCatId(),category.getName());
        }
    }

    private void findParentPath(Long catelogId, List<Long> paths) {
        paths.add(catelogId);
        CategoryEntity categoryEntity = this.getById(catelogId);
        if(categoryEntity == null) {
            return;
        }
        if (categoryEntity.getParentCid() != 0) {
            findParentPath(categoryEntity.getParentCid(), paths);
        }
    }

    /**
     * 递归查找所有菜单的子菜单
     *
     * @param rootCategories
     * @param allCategories
     * @return
     */
    private List<CategoryEntity> getChildCategories(CategoryEntity rootCategories, List<CategoryEntity> allCategories) {
        return allCategories.stream()
                .filter(categoryEntity -> categoryEntity.getParentCid().equals(rootCategories.getCatId()))
                //找到子菜单
                .peek(categoryEntity -> categoryEntity.setChildren(getChildCategories(categoryEntity, allCategories)))
                //进行排序
                .sorted(Comparator.comparingInt(menu -> Objects.isNull(menu.getSort()) ? 0 : menu.getSort()))
                .collect(Collectors.toList());
    }
}