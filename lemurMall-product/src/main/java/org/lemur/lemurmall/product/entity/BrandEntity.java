package org.lemur.lemurmall.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.hibernate.validator.constraints.URL;
import org.lemur.common.vaild.AddGroup;
import org.lemur.common.vaild.ListValue;
import org.lemur.common.vaild.UpdateGroup;
import org.lemur.common.vaild.UpdateStatusGroup;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * 品牌
 * 
 * @author Lemur
 * @email Lemur@gmail.com
 * @date 2023-02-27 15:08:47
 */
@Data
@TableName("pms_brand")
public class BrandEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 品牌id
	 */
	@NotNull(message = "修改必须指定品牌id", groups = {UpdateGroup.class})
	@Null(message = "新增不能指定id", groups = {AddGroup.class})
	@TableId
	private Long brandId;
	/**
	 * 品牌名
	 */
	@NotBlank(message = "品牌名必须提交", groups = {AddGroup.class, UpdateGroup.class})
	private String name;
	/**
	 * 品牌logo地址
	 */
	@URL(message = "品牌logo必须是一个合法的url地址", groups = {AddGroup.class, UpdateGroup.class})
	@NotEmpty(groups = {AddGroup.class})
	private String logo;
	/**
	 * 介绍
	 */
	private String descript;
	/**
	 * 显示状态[0-不显示；1-显示]
	 */
	@NotNull(groups = {AddGroup.class, UpdateStatusGroup.class})
	@ListValue(vals = {0, 1}, groups = {AddGroup.class, UpdateStatusGroup.class})
	private Integer showStatus;
	/**
	 * 检索首字母
	 */
	@NotEmpty(groups = {AddGroup.class})
	@Pattern(regexp = "^[a-zA-Z]$", message = "检索首字母必须为字母")
	private String firstLetter;
	/**
	 * 排序
	 */
	@NotNull(groups = {AddGroup.class})
	@Positive(message = "排序必须大于0")
	private Integer sort;

}
