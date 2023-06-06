package org.lemur.lemurmall.product;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.lemur.lemurmall.product.entity.BrandEntity;
import org.lemur.lemurmall.product.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * 1、引入oss-starter
 * 2、配置key、endpoint相关信息即可
 * 3、使用OSS进行相关操作
 */
@RunWith(SpringRunner.class)
@SpringBootTest
class LemurMallProductApplicationTests {
	@Autowired
	BrandService brandService;
	@Test
	void contextLoads() {
		List<BrandEntity> list = brandService.list();
		System.out.println("list = " + list);
	}
}
