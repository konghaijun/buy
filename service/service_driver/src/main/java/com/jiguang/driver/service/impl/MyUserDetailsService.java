/*
package com.jiguang.driver.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jiguang.driver.entity.Member;
import com.jiguang.driver.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

*
 * @Auther: 23091
 * @Date: 2023/3/8 16:46
 * @Description:




@Service("userDetailsService")
public class MyUserDetailsService implements UserDetailsService  {


    @Autowired
    private MemberService memberService;

    @Override
    public UserDetails loadUserByUsername(String s) throws
            UsernameNotFoundException {
        QueryWrapper<Member> wrapper = new QueryWrapper();
        wrapper.eq("user_name",s);
        Member users = memberService.getOne(wrapper);
        if(users == null) {
            throw new UsernameNotFoundException("用户名不存在！");
        }
        System.out.println(users);

        List<GrantedAuthority> auths =
                AuthorityUtils.commaSeparatedStringToAuthorityList("role");

      return   new User(users.getUserName(),
                new BCryptPasswordEncoder().encode(users.getUserPassword()),auths);

    }
}
*/
