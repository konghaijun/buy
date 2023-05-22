package com.jiguang.supplement.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.jiguang.commonutils.GuliException;
import com.jiguang.commonutils.R;
import com.jiguang.supplement.entity.UcenterMember;
import com.jiguang.supplement.service.UcenterMemberService;
import com.jiguang.supplement.utils.JwtUtils;
import com.jiguang.supplement.utils.WechatUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;



@RestController
@CrossOrigin //跨域
@RequestMapping("/supplement/umember")
public class UcenterMemberController {



    @Autowired
    UcenterMemberService ucenterMemberService;



    @ApiOperation(value = "根据token获取登录信息")
    @GetMapping("getLoginInfo")
    public R getLoginInfo(HttpServletRequest request){
        try {
            String memberId = JwtUtils.getMemberIdByJwtToken(request);
            UcenterMember member = ucenterMemberService.getById(memberId);
            return R.ok().data("user", member);
        }catch (Exception e){
            e.printStackTrace();
            throw new GuliException(201,"error");
        }
    }


    @RequestMapping(value = "/login",produces = "application/json")
    public R user_login(@RequestBody JSONObject json  ) {
        // 用户非敏感信息：rawData

        String code = json.getString("code");

        JSONObject rawDataJson=json.getJSONObject("rawData");

        //   JSONObject rawDataJson = JSON.parseObject(rawData);
        // 1.接收小程序发送的code
        // 2.开发者服务器 登录凭证校验接口 appi + appsecret + code

        JSONObject SessionKeyOpenId = WechatUtil.getSessionKeyOrOpenId(code);
        // 3.接收微信接口服务 获取返回的参数

        String openid = SessionKeyOpenId.getString("openid");
        System.out.println(openid);
        String sessionKey = SessionKeyOpenId.getString("session_key");

        // 4.校验签名 小程序发送的签名signature与服务器端生成的签名signature2 = sha1(rawData + sessionKey)


/* String signature2 = DigestUtils.sha1Hex(rawData + sessionKey);
        System.out.println(signature2);*//*

         */
/*  if (!signature.equals(signature2)) {
            return R.error().message("签名校验失败");
        }*/


        // 5.根据返回的User实体类，判断用户是否是新用户，是的话，将用户信息存到数据库；
        LambdaQueryWrapper<UcenterMember> lqw = new QueryWrapper<UcenterMember>().lambda();
        // LambdaQueryWrapper<Member> lqw = Wrappers.lambdaQuery();
        lqw.eq(UcenterMember::getOpenid, openid);
        UcenterMember user = ucenterMemberService.getOne(lqw);
        if (user == null) {
            // 用户信息入库
            String nickName = rawDataJson.getString("nickName");
            String avatarUrl = rawDataJson.getString("avatarUrl");
            user = new UcenterMember();
            user.setOpenid(openid);
            user.setAvatar(avatarUrl);
            user.setNickname(nickName);
            ucenterMemberService.save(user);
        }

        String token= JwtUtils.getJwtToken(user.getId(),user.getNickname());
        return R.ok().data("token",token);
    }





}

