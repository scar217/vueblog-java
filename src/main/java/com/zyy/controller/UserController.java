package com.zyy.controller;


import com.zyy.common.lang.Result;
import com.zyy.entity.User;
import com.zyy.service.UserService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 深林中的书海
 * @since 2023-03-13
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;
    @RequiresAuthentication //需要进行认证后才能访问
    @GetMapping("/index")
    public Result index(){
        User user = userService.getById(1L);
        return Result.success(200,"操作成功",user);
    }
    @PostMapping("/save")
    public Result save(@Validated @RequestBody User user){
        System.out.println(user);
        if (user.getId()!=null){
            boolean b = userService.updateById(user);
            if(b){
                return Result.success("更新成功!");
            }else {
                return Result.fail("更新失败!");
            }
        }else {
            boolean save = userService.save(user);
            if(save){
                return Result.success("保存成功!");
            }else {
                return Result.fail("保存失败!");
            }
        }
    }
}
