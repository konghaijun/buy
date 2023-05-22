package com.jiguang.supplement.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Auther: 23091
 * @Date: 2023/4/23 21:37
 * @Description:
 */
@Component
public  class BaiDu implements InitializingBean {
    //读取配置文件内容

    @Value("${BAIDU.CLIENT_ID}")
    private String keyId;

    @Value("${BAIDU.CLIENT_SECRET}")
    private String keySecret;


    //定义公开静态常量

    public static String BAIDU_KEY_ID;
    public static String BAIDU_KEY_SECRET;


    @Override
    public void afterPropertiesSet() throws Exception {

        BAIDU_KEY_ID = keyId;
        BAIDU_KEY_SECRET = keySecret;

    }
}
