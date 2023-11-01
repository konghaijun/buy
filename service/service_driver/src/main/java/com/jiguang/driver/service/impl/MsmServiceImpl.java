package com.jiguang.driver.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.jiguang.driver.service.MsmService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.util.Map;

@Service
public class MsmServiceImpl implements MsmService {

    @Override
    public boolean send(Map<String,Object> map, String phone) {
        if(StringUtils.isEmpty(phone)) {return false;}

        DefaultProfile profile =
                DefaultProfile.getProfile("default", "",
                        "");
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        //request.setProtocol(ProtocolType.HTTPS);
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2023-05-25");
        request.setAction("SendSms");

        request.putQueryParameter("PhoneNumbers", phone);   //手机号
        request.putQueryParameter("SignName", "Kong的个人网站");    //签名名称
        request.putQueryParameter("TemplateCode", "SMS_268560737");  //模板名称
        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(map));  //验证码转换json数据

        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
            return response.getHttpResponse().isSuccess();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return false;
    }
}
