package org.lemur.lemurmall.product;

import org.junit.jupiter.api.Test;
import org.lemur.lemurmall.product.entity.BrandEntity;
import org.lemur.lemurmall.product.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

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
