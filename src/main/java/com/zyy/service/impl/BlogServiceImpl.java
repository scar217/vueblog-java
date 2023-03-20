package com.zyy.service.impl;

import com.zyy.entity.Blog;
import com.zyy.mapper.BlogMapper;
import com.zyy.service.BlogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 深林中的书海
 * @since 2023-03-13
 */
@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements BlogService {

}
