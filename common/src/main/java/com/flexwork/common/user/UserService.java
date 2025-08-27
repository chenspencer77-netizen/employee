package com.flexwork.common.user;

/**
 * User Service Dubbo Interface
 */
public interface UserService {

    /**
     * Find user by username
     * @param username the username
     * @return LoginUser
     */
    LoginUser findByUsername(String username);
}
