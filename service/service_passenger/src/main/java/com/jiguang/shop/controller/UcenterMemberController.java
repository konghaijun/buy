package com.jiguang.shop.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.jiguang.commonutils.JwtUtils;
import com.jiguang.commonutils.R;
import com.jiguang.servicebase.handle.GuliException;
import com.jiguang.shop.entity.UcenterMember;
import com.jiguang.shop.service.UcenterMemberService;
import com.jiguang.shop.utils.WechatUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@RestController
@CrossOrigin //跨域
@RequestMapping("/shop/member")
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
    public R user_login(@RequestBody JSONObject json) {
        // 用户非敏感信息：rawData

        String code = json.getString("code");
        // String rawData = json.getString("rawData");
        // System.out.println(rawData);
        //  String signature=json.getString("signature");

        // JSONObject jsonArray=jsonObject.get("list");
        JSONObject rawDataJson=json.getJSONObject("rawData");

        //   JSONObject rawDataJson = JSON.parseObject(rawData);
        // 1.接收小程序发送的code
        // 2.开发者服务器 登录凭证校验接口 appi + appsecret + code

        JSONObject SessionKeyOpenId = WechatUtil.getSessionKeyOrOpenId(code);
        // 3.接收微信接口服务 获取返回的参数
        String openid = SessionKeyOpenId.getString("openid");
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



    //修改 昵称
    @PostMapping("updateU")
    public  R updateU(@RequestBody UcenterMember   ucenterMember){
        QueryWrapper<UcenterMember>  wrapper=new QueryWrapper<>();
        wrapper.eq("id",ucenterMember.getId());
      boolean a=  ucenterMemberService.update(ucenterMember,wrapper);
      if(a){
        return  R.ok().message("修改成功");}else{ return R.error().message("修改失败");}
    }



     @GetMapping("count")
    public R count(){
        int number=ucenterMemberService.count(null);
        return R.ok().data("number",number);
     }






}

