
package com.jiguang.shop.config;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.jiguang.shop.entity.YmlParament;
import org.springframework.context.annotation.Bean;




public class AlipayConfig {



    @Bean
    public AlipayClient setAlipayClient() {
        return new DefaultAlipayClient(
                YmlParament.ServerUrl,
                YmlParament.APP_ID,
                YmlParament.APP_Privatekey,
                YmlParament.Format,
                YmlParament.Charset,
                //这里不要搞错，这里是应用公钥而不是支付宝公钥
                YmlParament.APP_Publickey,
                YmlParament.SignType
        );
    }


/**
     * 设置网页支付请求基础参数
     * @return
     */

    @Bean
    public AlipayTradeWapPayRequest setAlipayTradeWapPayRequest() {
        AlipayTradeWapPayRequest atwpr = new AlipayTradeWapPayRequest();
        atwpr.setApiVersion(YmlParament.ApiVersion);
        atwpr.setProdCode(YmlParament.ProdCode);// 产品码
        atwpr.setTerminalInfo(YmlParament.TerminalInfo);// 终端信息
        atwpr.setTerminalType(YmlParament.TerminalType);// 终端信息类型
        return atwpr;
    }


}

