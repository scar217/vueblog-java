package com.zyy.controller;

import cn.hutool.core.bean.BeanUtil;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.zyy.common.lang.Result;
import com.zyy.entity.Blog;
import com.zyy.entity.Gender;
import com.zyy.entity.Statistic;
import com.zyy.entity.User;

import com.zyy.service.BlogService;
import com.zyy.service.UserService;
import com.zyy.util.ShiroUtil;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;


@RestController
@RequestMapping("/manage")
public class ManageController {
    @Autowired
    private UserService userService;
    @Autowired
    private BlogService blogService;
//    用户管理页
    @GetMapping("/findAll")
    public Result findAll(@RequestParam(defaultValue = "1") Integer currentPage){
        //分页查询接口
        Page page = new Page(currentPage,5);
        IPage PageData = userService.page(page, new QueryWrapper<User>().orderByDesc("created"));
        if(PageData != null){
            return Result.success(PageData);
        }else {
            return Result.fail("初始化失败!请联系开发者");
        }
    }
    @GetMapping("/deleteOne")
    public Result deleteOne(@RequestParam Long id){
        boolean b = userService.removeById(id);
        if(b){
            return Result.success(200,"删除成功!",null);
        }else {
            return Result.fail("删除失败,稍后再试!");
        }
    }
//    主页
    @GetMapping("/home/statistic")
    public Result statistic(){
        ArrayList<Gender> list = new ArrayList<>();
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("status",1);
        int countMale = userService.count(wrapper);
        int total = userService.count();
        int countFreMale = total - countMale;
        Gender gender1 = new Gender();
        Gender gender2 = new Gender();
        gender1.setName("Male");
        gender1.setValue(countMale);
        gender2.setName("FreMale");
        gender2.setValue(countFreMale);
        list.add(gender1);
        list.add(gender2);
        return Result.success(list);
    }
    @GetMapping("/home/initial")
    public Result initial(){
        Statistic st = new Statistic();
        //博客注册人数，博客量，今日注册人数，博客访问量(未完成)
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        QueryWrapper<User> queryWrapper = wrapper.inSql("id", "SELECT id FROM m_user WHERE DATEDIFF(created,NOW())=0");
        st.setRegister_now(userService.count(queryWrapper));
        int register = userService.count();
        st.setRegister(register);
        st.setVisitorNum(register+50);
        st.setBlogsNum(blogService.count());
        return Result.success(st);
    }
    //博客管理页
    @GetMapping("/blogs")
    public Result list(@RequestParam(defaultValue = "1") Integer currentPage){
        //分页查询接口
        Page page = new Page(currentPage,5);
        IPage pageData = blogService.page(page, new QueryWrapper<Blog>().orderByDesc("created"));
        return Result.success(pageData);
    }
    @PostMapping("/edit")
    public Result edit(@Validated @RequestBody Blog blog) {
        Blog temp = null;
        //如果有id则是编辑
        if(blog.getId() != null) {
            //将数据库的内容传递给temp
            temp = blogService.getById(blog.getId());
        } else {
            temp = new Blog();
            temp.setUserId(ShiroUtil.getProfile().getId());
            temp.setCreated(LocalDateTime.now());
            temp.setStatus(0);
        }
        //修改值的覆盖
        BeanUtil.copyProperties(blog, temp, "id", "userId", "created", "status");
        blogService.saveOrUpdate(temp);
        return Result.success(null);
    }
    @GetMapping("/blogDel")
    public Result del(@RequestParam Long id){
        System.out.println(id);
        boolean b = blogService.removeById(id);
//        判断是否为空 为空则断言异常
        if(b){
            return Result.success("文章删除成功");
        }else{
            return Result.fail("文章删除失败");
        }
    }

}
