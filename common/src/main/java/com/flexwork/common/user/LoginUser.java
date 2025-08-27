package com.flexwork.common.user;

import lombok.Data;
import java.io.Serializable;

@Data
public class LoginUser implements Serializable {
    private static final long serialVersionUID = 1L;

    private String userId;
    private String username;
    private String password; // In a real app, this would be a hash
    private String status;
}
