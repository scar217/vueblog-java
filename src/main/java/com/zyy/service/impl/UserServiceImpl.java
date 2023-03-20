package com.zyy.service.impl;

import cn.hutool.crypto.SecureUtil;
import com.zyy.entity.User;
import com.zyy.mapper.UserMapper;
import com.zyy.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.catalina.security.SecurityUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 深林中的书海
 * @since 2023-03-13
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Override
    public boolean save(User entity) {
        LocalDateTime createTime = LocalDateTime.now();
        String password = SecureUtil.md5(entity.getPassword());
        entity.setPassword(password);
        entity.setCreated(createTime);
        if(StringUtils.isEmpty(entity.getAvatar())){
            entity.setAvatar("https://image.meiye.art/pic_1629276323438_m7eEmhsPULUhqbhXJX7l?imageMogr2/thumbnail/470x/interlace/1");
        }
        return super.save(entity);
    }
}
