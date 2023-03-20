package com.zyy.controller;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.map.MapUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zyy.common.dto.LoginDto;
import com.zyy.common.lang.Result;
import com.zyy.entity.User;
import com.zyy.service.UserService;
import com.zyy.util.JwtUtils;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
@RestController
public class AccountController {
    @Autowired
    UserService userService;
    @Autowired
    JwtUtils jwtUtils;
    @PostMapping("/login")
    public Result login(@Validated @RequestBody LoginDto loginDto, HttpServletResponse response){
        User user = userService.getOne(new QueryWrapper<User>().eq("username", loginDto.getUsername()));
        //断点处理
        Assert.notNull(user,"用户不存在");
        if(!user.getPassword().equals(SecureUtil.md5(loginDto.getPassword()))){
            return Result.fail("密码不正确");
        }
        String jwt = jwtUtils.generateToken(user.getId());
        response.setHeader("Authorization", jwt);
        response.setHeader("Access-Control-Expose-Headers", "Authorization");

        return Result.success(MapUtil.builder()
                .put("id", user.getId())
                .put("username", user.getUsername())
                .put("avatar", user.getAvatar())
                .put("email", user.getEmail())
                .map()
        );
    }
    @RequiresAuthentication
    @GetMapping("/logout")
    public Result logout(){
//        用户退出登录时，Shiro用户必须执行logout()，必须要注销Session信息，避免影响下一次用户认证和授权。
        SecurityUtils.getSubject().logout();
        return Result.success(null);
    }

}
