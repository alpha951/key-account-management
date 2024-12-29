package org.kamsystem.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ApiError {
    INVALID_REQUEST_BODY(1001),
    INVALID_MOBILE(1002),
    INVALID_REQUEST(1003),
    SERVER_ISSUE(1004),
    RESOURCE_NOT_FOUND(1005),
    INVALID_REQUEST_PARAMS(1006);

    private final int code;
}
