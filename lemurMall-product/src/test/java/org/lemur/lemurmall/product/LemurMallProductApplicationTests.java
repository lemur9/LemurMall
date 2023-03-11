package org.lemur.lemurmall.product;

import com.aliyun.oss.*;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.lemur.lemurmall.product.entity.BrandEntity;
import org.lemur.lemurmall.product.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
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

	@Autowired
	OSS ossClient;

	@Test
	void testUpload() {
		// 填写Bucket名称，例如examplebucket。
		String bucketName = "lemur-mall";
		// 填写Object完整路径，完整路径中不能包含Bucket名称，例如exampledir/exampleobject.txt。
		String objectName = "01.jpeg";
		// 填写本地文件的完整路径，例如D:\\localpath\\examplefile.txt。
		// 如果未指定本地路径，则默认从示例程序所属项目对应本地路径中上传文件流。
		String filePath = "C:\\Users\\14020\\Pictures\\头像\\default.jpg";

		// 创建OSSClient实例。
//		OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

		try {
			InputStream inputStream = Files.newInputStream(Paths.get(filePath));
			// 创建PutObjectRequest对象。
			PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, objectName, inputStream);
			// 设置该属性可以返回response。如果不设置，则返回的response为空。
			putObjectRequest.setProcess("true");
			// 创建PutObject请求。
			PutObjectResult result = ossClient.putObject(putObjectRequest);
			// 如果上传成功，则返回200。
			System.out.println(result.getResponse().getStatusCode());
		} catch (OSSException oe) {
			System.out.println("Caught an OSSException, which means your request made it to OSS, "
					+ "but was rejected with an error response for some reason.");
			System.out.println("Error Message:" + oe.getErrorMessage());
			System.out.println("Error Code:" + oe.getErrorCode());
			System.out.println("Request ID:" + oe.getRequestId());
			System.out.println("Host ID:" + oe.getHostId());
		} catch (ClientException ce) {
			System.out.println("Caught an ClientException, which means the client encountered "
					+ "a serious internal problem while trying to communicate with OSS, "
					+ "such as not being able to access the network.");
			System.out.println("Error Message:" + ce.getMessage());
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			if (ossClient != null) {
				ossClient.shutdown();
			}
		}
	}

	@Test
	void contextLoads() {
		List<BrandEntity> list = brandService.list();
		System.out.println("list = " + list);
	}

}
