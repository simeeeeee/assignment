package com.lfin.assignment.common.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@RequiredArgsConstructor
@ToString
public enum ResultStatus {
    REQUEST_SUCCESS("200", "정상적으로 처리되었습니다."),
    CLIENT_NOT_FOUND_RESOURCE("1001", "리소스를 찾을 수 없습니다.");

    private final String status;
    private final String message;
}
