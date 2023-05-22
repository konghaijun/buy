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
public  class TengXun implements InitializingBean {
    //读取配置文件内容

    @Value("${TENGXUN.SECRET_ID}")
    private String keyId;

    @Value("${TENGXUN.SECRET_KEY}")
    private String keySecret;


    //定义公开静态常量

    public static String TENGXUN_KEY_ID;
    public static String TENGXUN_KEY_SECRET;


    @Override
    public void afterPropertiesSet() throws Exception {

        TENGXUN_KEY_ID = keyId;
        TENGXUN_KEY_SECRET = keySecret;

    }
}
