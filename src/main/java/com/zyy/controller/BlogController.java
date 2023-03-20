package com.zyy.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zyy.common.lang.Result;
import com.zyy.entity.Blog;
import com.zyy.service.BlogService;
import com.zyy.util.ShiroUtil;
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
public class BlogController {
    @Autowired
    BlogService blogService;

    @GetMapping("/blogs")
    public Result list(@RequestParam(defaultValue = "1") Integer currentPage){
        //分页查询接口
        Page page = new Page(currentPage,5);
        IPage pageData = blogService.page(page, new QueryWrapper<Blog>().orderByDesc("created"));

        return Result.success(pageData);
    }

    @GetMapping("/blog/{id}")
    public Result detail(@PathVariable(name = "id") Long id) {
        Blog blog = blogService.getById(id);
        Assert.notNull(blog, "该博客已删除！");
        return Result.success(blog);
    }

    @RequiresAuthentication //需要登录后才可以访问编辑页面
    @PostMapping("/blog/edit")
//    没有id是添加操作，有id则是编辑操作
    public Result edit(@Validated @RequestBody Blog blog) {
        Blog temp = null;
        //如果有id则是编辑
        if(blog.getId() != null) {
            //将数据库的内容传递给temp
            temp = blogService.getById(blog.getId());
            //设置只能编辑自己的文章
            Assert.isTrue(temp.getUserId() == ShiroUtil.getProfile().getId(), "没有权限编辑");
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

    @PostMapping("/blogDel")
    public Result del(@RequestBody Blog blog){
        Long id = blog.getId();
        boolean b = blogService.removeById(id);
//        判断是否为空 为空则断言异常
        if(b){
            return Result.success("文章删除成功");
        }else{
            return Result.fail("文章删除失败");
        }
    }

}
