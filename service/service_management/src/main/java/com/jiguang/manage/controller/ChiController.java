package com.jiguang.manage.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jiguang.manage.entity.Chi;
import com.jiguang.manage.service.ChiService;
import com.jiguang.manage.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin //跨域
@RestController
@RequestMapping("/manage/chi")
public class ChiController {

    @Autowired
    ChiService chiService;

    @GetMapping("getSeng")
    public R getSeng(){
        QueryWrapper<Chi> wrapper=new QueryWrapper<>();
        wrapper.eq("Pid",0);
        List<Chi> list=chiService.list(wrapper);
        return R.ok().data("list",list);

    }

    @GetMapping("getShi/{Pid}")
    public R getShi(@PathVariable Integer Pid){
        QueryWrapper<Chi> wrapper=new QueryWrapper<>();
        wrapper.eq("Pid",Pid);
        List<Chi> list=chiService.list(wrapper);
        return R.ok().data("list",list);

    }
}

