package com.jiguang.driver.service.impl;

import com.jiguang.driver.entity.Member;
import com.jiguang.driver.mapper.MemberMapper;
import com.jiguang.driver.service.MemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;


@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {

}
