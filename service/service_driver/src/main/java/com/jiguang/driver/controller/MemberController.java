package com.jiguang.driver.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jiguang.commonutils.JwtUtils;
import com.jiguang.commonutils.R;
import com.jiguang.driver.entity.Member;
import com.jiguang.driver.entity.Vo.LoginVo;
import com.jiguang.driver.entity.Vo.RegisterVo;
import com.jiguang.driver.service.MemberService;
import com.jiguang.servicebase.handle.GuliException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;



@RestController
@CrossOrigin //跨域
@RequestMapping("/driver/member")
public class MemberController {

    @Autowired
    MemberService memberService;


    //根据token获取用户信息
    @GetMapping("getLoginInfo")
    public R getLoginInfo(HttpServletRequest request) {
        try {
            String memberId = JwtUtils.getMemberIdByJwtToken(request);

            QueryWrapper<Member> wrapper = new QueryWrapper<>();
            wrapper.eq("user_name", memberId);
            Member member = memberService.getOne(wrapper);
            return R.ok().data("user", member);
        } catch (Exception e) {
            e.printStackTrace();
            throw new GuliException(201, "error");
        }
    }


    //登录
    @PostMapping("login")
    public R login(@RequestBody LoginVo loginVo) {
        QueryWrapper<Member> wrapper = new QueryWrapper<>();
        wrapper.eq("user_name", loginVo.getUserName());
        wrapper.eq("user_password", loginVo.getUserPassword());
        Member user = memberService.getOne(wrapper);
        String token = JwtUtils.getJwtToken(user.getUserName(), user.getUserNickname());
        return R.ok().data("token", token);
    }


    //注册
    @PostMapping("register")
    public R register(@RequestBody RegisterVo user) {
        Member m = new Member();
        BeanUtils.copyProperties(user, m);
        memberService.save(m);
        return R.ok().message("注册成功");
    }


    //根据司机id 查询司机信息
    @GetMapping("getById/{id}")
    public Member getById(@PathVariable Long id) {
        return memberService.getById(id);
    }


    //查看司机数量
    @GetMapping("count")
    public R count() {
        int number = memberService.count(null);
        return R.ok().data("number", number);
    }


    //分页查询所有司机信息
    @GetMapping("select/{pages}")
    public R select(@PathVariable Integer pages) {
        Page<Member> page = new Page<>(pages, 10);
        QueryWrapper<Member> queryWrapper = new QueryWrapper<>();
        // 设置ID倒序排序
        queryWrapper.orderByDesc("id");
        memberService.page(page, queryWrapper);
        return R.ok().data("list", page);
    }


    @GetMapping("months")
    public R getPastSixMonths() {



        Map map=new HashMap();
        List tx=new ArrayList();
        List ty=new ArrayList();




        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
        for (int i = 5; i >= 0; i--) {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MONTH, -i);
            Date date = calendar.getTime();
            String result = dateFormat.format(date);

            tx.add(tx(result));
            QueryWrapper<Member> wrapper=new QueryWrapper<>();
            wrapper.like("gmt_create",result);
            List<Member> list=memberService.list(wrapper);
            ty.add(list.size());
        }
        map.put("tx",tx);
        map.put("ty",ty);
        return  R.ok().data("map",map);
    }

    public String tx(String s){
        String s1=s.substring(5,7);
        System.out.println(s1);
        if(s1.equals("01")) {
            return "一月";
        }if(s1.equals("02")) {
            return "二月";
        }if(s1.equals("03")) {
            return "三月";
        }if(s1.equals("04")) {
            return "四月";
        }if(s1.equals("05")) {
            return "五月";
        }if(s1.equals("06")) {
            return "六月";
        }if(s1.equals("07")) {
            return "七月";
        }if(s1.equals("08")) {
            return "八月";
        }if(s1.equals("09")) {
            return "九月";
        }if(s1.equals("10")) {
            return "十月";
        }if(s1.equals("11")) {
            return "十一月";
        }if(s1.equals("12")) {
            return "十二月";
        } return "0";
    }


    @GetMapping("probability")
    public R getProbability(){

        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
        String formattedDate = date.format(formatter);

         QueryWrapper<Member> wrapper=new QueryWrapper<>();
         wrapper.like("gmt_create",formattedDate);
         List<Member> list=memberService.list(wrapper);
         List<Member> list1=memberService.list(null);

         int num=list.size()*100/list1.size();
         String s=num+"%";

        return  R.ok().data("num",s);
    }



    //对比昨天
    @GetMapping("compare")
    public R compare(){

        LocalDate thisMonth = LocalDate.now();
        DateTimeFormatter yearMonthFormatter = DateTimeFormatter.ofPattern("yyyy-MM");
        LocalDate lastMonth = thisMonth.minusMonths(1);

        String ThisMonth = thisMonth.format(yearMonthFormatter);
        String LastMonth = lastMonth.format(yearMonthFormatter);

        QueryWrapper<Member> wrapper=new QueryWrapper<>();
        QueryWrapper<Member> wrapper1=new QueryWrapper<>();
         wrapper.like("gmt_create",ThisMonth);
         wrapper1.like("gmt_create",LastMonth);
         List<Member> thism=memberService.list(wrapper);
         List<Member> lastm=memberService.list(wrapper1);

        return R.ok().data("mum",thism.size()-lastm.size());

    }



    //查看司机数量
    @GetMapping("count/{day}")
    public R count(@PathVariable String day) {
        QueryWrapper<Member> wrapper=new QueryWrapper<>();
        wrapper.like("gmt_create",day);
        int number = memberService.count(wrapper);
        return R.ok().data("number", number);
    }




@GetMapping("getDay")
    public R getDay(){
    LocalDate date = LocalDate.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    String  format = date.format(formatter);
    List<Member> members=new ArrayList<>();
    QueryWrapper<Member> wrapper=new QueryWrapper<>();
    wrapper.like("gmt_create",format);
    List<Member> list=memberService.list(wrapper);
    return R.ok().data("number",list.size());
}


//是否禁用司机
    @PostMapping("status")
    public R status(@RequestBody Member member){
        UpdateWrapper wrapper=new UpdateWrapper();
        wrapper.eq("id",member.getId());

       boolean f=memberService.update(member,wrapper);
       if(f) {
           return R.ok().message("修改成功");
       }else
       {return  R.error().message("修改失败");}
    }



//模糊查询司机
//场站搜索根据名字
@GetMapping("selectName")
public R selectName(@RequestParam("name")String name) {

        QueryWrapper<Member> wrapper=new QueryWrapper<>();
        wrapper.like("user_nickname",name);

        List<Member> list=memberService.list(wrapper);


     return R.ok().data("list",list);
}

}
