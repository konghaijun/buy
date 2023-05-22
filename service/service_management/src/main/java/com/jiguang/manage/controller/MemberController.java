package com.jiguang.manage.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jiguang.manage.entity.Member;

import com.jiguang.manage.entity.Vo.MemberVo;
import com.jiguang.manage.entity.Vo.loginVo;
import com.jiguang.manage.service.MemberService;
import com.jiguang.manage.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2023-03-17
 */
@CrossOrigin //跨域
@RestController
@RequestMapping("/manage/member")
public class MemberController {

    @Autowired
    MemberService memberService;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    //注册
    @PostMapping("add")
    public R add(@RequestBody  Member member) {
        boolean f = memberService.save(member);
        if (f) {
            return R.ok().message("注册成功");
        } else {
            return R.error().message("注册失败");
        }
    }




     //登录
    @PostMapping("login")
    public R login(@RequestBody MemberVo memberVo){

        QueryWrapper<Member> wrapper=new QueryWrapper<>();
        wrapper.eq("username",memberVo.getUsername());
        wrapper.eq("password",memberVo.getPassword());
        Member member=memberService.getOne(wrapper);
        if(member!=null) {
            return R.ok().data("user",member);
        }else  {
            return R.error().message("账号或密码错误");
        }
        }



    @PostMapping("loginMobile")
    public R loginMobile(@RequestBody loginVo loginVo) {


        String code = redisTemplate.opsForValue().get(loginVo.getMobile());
        if (code.equals(loginVo.getCode())) {
            QueryWrapper<Member> wrapper = new QueryWrapper<>();
            wrapper.eq("phone", loginVo.getMobile());
            Member member = memberService.getOne(wrapper);

            if (member != null) {
                return R.ok().data("member", member);
            } else {
                return R.error().message("手机号不存在");
            }

        } else {
            return R.error().message("验证码有误");
        }

    }




    }









