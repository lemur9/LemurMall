package org.lemur.common.config;

import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement //开启事务
@MapperScan("org.lemur.lemurmall.*.dao")
public class MyBatisConfig {
    //引入分页插件
    @Bean
    public PaginationInnerInterceptor paginationInnerInterceptor() {
        PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor();
        // 设置请求的页面大于最大页后操作，true调回到首页，false继续请求默认false
        paginationInnerInterceptor.setOverflow(true);
        // 设置最大单页限制数量，默认500条，-1不受限制
        paginationInnerInterceptor.setMaxLimit(1000L);
        return paginationInnerInterceptor;
    }
}
