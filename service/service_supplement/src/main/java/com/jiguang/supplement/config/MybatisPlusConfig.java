package com.jiguang.supplement.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;

import com.jiguang.commonutils.MyMetaObjectHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Auther: 23091
 * @Date: 2023/4/15 00:10
 * @Description:
 */
@Configuration
public class MybatisPlusConfig {

    @Bean
    public MetaObjectHandler myMetaObjectHandler() {
        return new MyMetaObjectHandler();
    }


}
