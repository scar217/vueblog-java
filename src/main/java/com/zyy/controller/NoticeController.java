package com.zyy.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zyy.common.lang.Result;
import com.zyy.entity.Blog;
import com.zyy.entity.Notice;
import com.zyy.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 深林中的书海
 * @since 2023-03-19
 */
@RestController
@RequestMapping("/notice")
public class NoticeController {
    @Autowired
    NoticeService noticeService;
    @GetMapping("/find")
    public Result find(){
        QueryWrapper<Notice> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("created").last("limit 1");
        List<Notice> result = noticeService.list(queryWrapper);
        if(result != null){
            return Result.success(result);
        }else {
            return Result.fail("还没有最新公告!");
        }
    }
    @PostMapping("/save")
    public Result NoticeSave(@Validated @RequestBody Notice notice){
        System.out.println(notice);
        notice.setCreated(LocalDateTime.now());
        boolean b = noticeService.save(notice);
        if(b){
            return Result.success(null);
        }else {
            return Result.fail("发布失败,稍后再试!");
        }
    }
}
