package org.lemur.lemurmall.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * 1、整合MyBatis-Plus
 * 		1)、导入依赖
 * 			<dependency>
 * 				<groupId>com.baomidou</groupId>
 * 				<artifactId>mybatis-plus-boot-starter</artifactId>
 * 				<version>3.2.0</version>
 * 			</dependency>
 * 		2)、配置
 *			 1、配置数据源；
 * 				1)、导入数据库的驱动。
 * 				2)、在application.yml配置数据源相关信息
 * 			2、配置MyBatis-PLus;
 *				1)、使用@MapperScan
 * 				2)、告诉MyBatis-Plus,sql映射文件位置
 * 2、逻辑删除
 * 	1)、配置全局逻辑删除规则 （省略）
 * 	2)、配置逻辑删除的组件Bean （省略）
 * 	3)、给Bean加上逻辑删除注解@TableLogic
 * <p>
 * 3、JSR303
 * 	1)、给Bean添加校验注解:javax.validation.constraints,并定义自己的message提示
 * 	2)、yml中添加server:error:include-binding-errors:always
 * 	3)、开启校验功能@Validated
 * 		效果：校验错误以后会有默认的响应；
 * 	4)、给校验的Bean后紧跟一个BindingResult，就可以获取到校验的结果
 * 	5)、分组校验（多场景的复杂校验）
 *		1)、@NotBlank(message = "品牌名必须提交", groups = {AddGroup.class, UpdateGroup.class})
 *			给校验注解标注什么情况需要进行校验
 *		2)、@Validated({AddGroup.class})
 *		3)、默认没有指定分组的校验注解@NotBlank，在分组校验情况下@Validated({AddGroup.class})不生效，只会在@Validated失效
 * 	6)、自定义校验
 * 		1)、编写一个自定义的校验注解
 * 		2)、编写一个自定义的校验器	ConstraintValidator
 * 		3)、关联自定义的校验器和自定义的校验注解
 			 * @Documented
 			 * @Constraint(
 			 * validatedBy = {ListValueConstraintValidator.class}
 			 * )
 			 * @Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
 			 * @Retention(RetentionPolicy.RUNTIME)
 * <p>
 * 4、统一的异常处理
 * @ControllerAdvice
 * 1)、编写异常处理类，使用@ControllerAdvice。
 * 2)、使用@ExceptionHandler标注方法可以处理的异常。
 *
 */
@EnableFeignClients(basePackages = "org.lemur.lemurmall.product.feign")
@EnableDiscoveryClient
@SpringBootApplication
@ComponentScan("org.lemur")
public class LemurMallProductApplication {

	public static void main(String[] args) {
		SpringApplication.run(LemurMallProductApplication.class, args);
	}

}
