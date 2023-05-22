package com.jiguang.supplement.entity;


import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class YmlParament implements InitializingBean {
    //支付宝相关
    @Value("${ALIPAY.APP_ID}")
    private String appId;

    @Value("${ALIPAY.APP_PRIVATE_KEY}")
    private String appPrivatekey;

    @Value("${ALIPAY.APP_PUBLIC_KEY}")
    private String appPublickey;

    @Value("${ALIPAY.ALIPAY_PUBLIC_KEY}")
    private String alipayPublickey;

    @Value("${ALIPAY.SIGN_TYPE}")
    private String signType;

    @Value("${ALIPAY.CHARSET}")
    private String charset;

    @Value("${ALIPAY.FORMAT}")
    private String format;

    @Value("${ALIPAY.SERVER_URL}")
    private String serverUrl;

    @Value("${ALIPAY.ALIPAY_NOTIFY_URL}")
    private String alipayNotifyUrl;

    @Value("${ALIPAY.RETURN_URL}")
    private String returnUrl;

    @Value("${ALIPAY.API_VERSION}")
    private String apiVersion;

    @Value("${ALIPAY.PROD_CODE}")
    private String prodCode;

    @Value("${ALIPAY.TERMINAL_INFO}")
    private String terminalInfo;

    @Value("${ALIPAY.TERMINAL_TYPE}")
    private String terminalType;


    public  static  String   APP_ID;
    public  static  String   APP_Privatekey;
    public  static  String   APP_Publickey;
    public  static  String   AlipayPublickey;
    public  static  String   SignType;
    public  static  String   Charset;
    public  static  String   Format;
    public  static  String   ServerUrl;
    public  static  String   AlipayNotifyUrl;
    public  static  String   ReturnUrl;
    public  static  String   ApiVersion;
    public  static  String   ProdCode;
    public  static  String   TerminalInfo;
    public  static  String   TerminalType;




    @Override
    public void afterPropertiesSet() throws Exception {
        APP_ID = appId;
        APP_Privatekey=appPrivatekey;
        APP_Publickey=appPublickey;
        AlipayNotifyUrl=alipayNotifyUrl;
        AlipayPublickey=alipayPublickey;
        SignType=signType;
        Charset=charset;
        Format=format;
        ServerUrl=serverUrl;
        ReturnUrl=returnUrl;
        ApiVersion=apiVersion;
        ProdCode=prodCode;
        TerminalInfo=terminalInfo;
        TerminalType=terminalType;
    }
}
