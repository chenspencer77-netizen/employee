package com.flexwork.user.service;

import com.flexwork.common.user.LoginUser;
import com.flexwork.common.user.UserService;
import org.apache.dubbo.config.annotation.DubboService;

@DubboService
public class UserServiceImpl implements UserService {

    @Override
    public LoginUser findByUsername(String username) {
        if ("admin".equals(username)) {
            LoginUser user = new LoginUser();
            user.setUserId("1");
            user.setUsername("admin");
            // In a real app, this would be a hashed password from the database
            user.setPassword("password");
            user.setStatus("active");
            return user;
        }
        return null;
    }
}
