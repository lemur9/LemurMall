package org.lemur.lemurmall.coupon;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * 1、如何使用Nacos作为配置中心统一管理配置
 * 2、引入依赖，
 *      <dependency>
 * 			<groupId>com.alibaba.cloud</groupId>
 * 			<artifactId>spring-cloud-starter-alibaba-nacos-config</artifactId>
 * 			<version>2.2.3.RELEASE</version>
 * 		</dependency>
 * 		<dependency>
 * 			<groupId>org.springframework.cloud</groupId>
 * 			<artifactId>spring-cloud-starter-bootstrap</artifactId>
 * 			<version>4.0.0</version>
 * 		</dependency>
 * 3、创建一个bootstrap.properties
 * 	spring.application.name=lemurMall-coupon
 *  spring.cloud.nacos.config.server-addr=47.100.46.162:8848
 * 4、需要给配置中心默认添加一个叫 数据集(DATA ID) 默认规则，应用名.properties
 * 5、给应用名.properties 添加任何配置
 * 6、动态获取配置
 *      @RefreshSocp:动态获取并刷新配置
 *      @Value("${配置项名}"):获取到配置
 * 7、如果配置中心和当前应用的配置文件中都配置了相同项，优先使用配置中心的配置
 * <p>
 *
 * 细节：
 *      1.命名空间：配置隔离。
 *          默认：public（保留空间）；默认新增的所有配置都在public空间
 *          1.开发，测试，生产：利用命名空间来做环境隔离
 *          注意：在bootstrap.properties;配置上，需要使用哪个命名空间下的配置，
 *              spring.cloud.nacos.config.namespace=d554beb1-3a34-4787-beca-7ab4eb936ae9
 *          2.每一个微服务之间互相隔离配置，每一个微服务都创建自己的命名空间，只加载自己命名空间下的所有配置
 *      2.配置集:所有的配置集合
 *      3.配置集ID:类似文件名
 *          Data ID:类似文件名
 *      4.配置分组：
 *          默认所有的配置集都属于：DEFAULT_GROUP
 *      5.项目使用中：每个微服务创建自己的命名空间，使用配置分组区分环境，dev，prod
 * <p>
 *
 *同时加载多个配置集
 * 1.微服务任何配置信息，任何配置文件都可以放到配置中心中
 * 2.只需要在bootstrap.properties说明加载配置中心中哪些配置文件即可
 * 3.@Value，@ConfigurationProperties。。。
 * 4.以前SpringBoot任何方法从配置文件中获取值，都能使用
 * 5.配置中心有的优先使用配置中心中的，
 */
@EnableDiscoveryClient
@SpringBootApplication
public class LemurMallCouponApplication {

    public static void main(String[] args) {
        SpringApplication.run(LemurMallCouponApplication.class, args);
    }

}
