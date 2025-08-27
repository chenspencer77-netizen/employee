package com.flexwork.auth.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.flexwork.common.api.CommonResult;
import com.flexwork.common.user.LoginUser;
import com.flexwork.common.user.UserService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {

    @DubboReference
    private UserService userService;

    /**
     * Login
     */
    @PostMapping("/login")
    public CommonResult<String> login(@RequestBody LoginRequest request) {
        log.info("Attempting login for user: {}", request.username());

        LoginUser user = userService.findByUsername(request.username());

        if (user == null) {
            log.warn("Login failed for user: {}. User not found.", request.username());
            return CommonResult.failed("Invalid username or password");
        }

        // In a real app, you'd use a password encoder
        if (request.password().equals(user.getPassword())) {
            StpUtil.login(user.getUserId());
            String token = StpUtil.getTokenValue();
            log.info("Login successful for user: {}, token created.", request.username());
            return CommonResult.success("Login successful", token);
        }

        log.warn("Login failed for user: {}. Invalid password.", request.username());
        return CommonResult.failed("Invalid username or password");
    }

    /**
     * Check login status
     */
    @PostMapping("/isLogin")
    public CommonResult<Boolean> isLogin() {
        return CommonResult.success("User is logged in", StpUtil.isLogin());
    }

    /**
     * Logout
     */
    @PostMapping("/logout")
    public CommonResult<Void> logout() {
        StpUtil.logout();
        return CommonResult.success("Logout successful", null);
    }
}

record LoginRequest(String username, String password) {
}
