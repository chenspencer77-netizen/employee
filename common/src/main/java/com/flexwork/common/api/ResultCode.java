package com.flexwork.common.api;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Common result codes
 */
@Getter
@AllArgsConstructor
public enum ResultCode implements IErrorCode {
    SUCCESS(200, "Operation succeeded"),
    FAILED(500, "Operation failed"),
    VALIDATE_FAILED(400, "Parameter validation failed"),
    UNAUTHORIZED(401, "Not logged in or token has expired"),
    FORBIDDEN(403, "No relevant permissions");

    private final long code;
    private final String message;
}
