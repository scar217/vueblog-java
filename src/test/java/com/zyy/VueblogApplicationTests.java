package com.zyy;

import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zyy.common.lang.Result;
import com.zyy.entity.Blog;
import com.zyy.entity.Notice;
import com.zyy.entity.User;
import com.zyy.mapper.UserMapper;
import com.zyy.service.BlogService;
import com.zyy.service.NoticeService;
import com.zyy.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@SpringBootTest
class VueblogApplicationTests {
    @Autowired
    UserService userService;
    @Autowired
    BlogService blogService;
    @Autowired
    NoticeService noticeService;
    @Test
    void contextLoads() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        QueryWrapper<User> queryWrapper = wrapper.inSql("id", "SELECT id FROM m_user WHERE DATEDIFF(created,NOW())=0");
        Integer count = userService.count(queryWrapper);
        System.out.println("当天注册人数:"+count);
        System.out.println("-----------------------------------");
    }
    @Test
    void Test01(){
        QueryWrapper<Notice> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("created").last("limit 1");
        List<Notice> notice = noticeService.list(queryWrapper);
        notice.forEach(System.out::println);
    }

}
