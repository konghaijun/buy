package com.jiguang.driver.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.jiguang.commonutils.R;
import com.jiguang.driver.entity.Lock;
import com.jiguang.driver.service.LockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author testjava
 * @since 2023-04-04
 */
@RestController
@RequestMapping("/driver/lock")
public class LockController {

     @Autowired
    LockService lockService;


    @PostMapping("open")
    public R open(@RequestBody Lock lock){
        UpdateWrapper wrapper=new UpdateWrapper();
        wrapper.eq("machine_number",lock.getMachineNumber());
        boolean f=lockService.update(lock,wrapper);
       if(f) {
           return R.ok().message("修改成功");
       }else {
           return R.error().message("修改失败");
       }
    }



    @GetMapping("get/{number}")
    public Lock get(@PathVariable String number)
    {
        QueryWrapper<Lock>wrapper=new QueryWrapper<>();
        wrapper.eq("machine_number",number);
        Lock user=lockService.getOne(wrapper);
        return user;
    }


    @PostMapping("close/{number}")
    public String close(@PathVariable String number){
UpdateWrapper<Lock> wrapper=new UpdateWrapper<>();
        wrapper.eq("machine_number",number);
        Lock lock=new Lock();
        lock.setLocker(0);
        lockService.update(lock,wrapper);
        return "ok";
    }


}

