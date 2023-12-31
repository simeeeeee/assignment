package com.lfin.assignment.common.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@RequiredArgsConstructor
@ToString
public enum ResultStatus {
    REQUEST_SUCCESS("2000", "정상적으로 처리되었습니다."),

    INTERNAL_SERVER_ERROR("5000", "내부 서버 오류가 발생했습니다."),

    CLIENT_INVALID_REQUEST("1002", "형식이 올바르지 않습니다."),
    CLIENT_NOT_CHANGE_EMAIL_REQUEST("1003", "이메일은 변경할 수 없습니다."),
    CLIENT_NOT_MATCH_PASSWORD_REQUEST("1005", "기존 비밀번호와 일치하지 않습니다."),
    CLIENT_EXIST_EMAIL_REQUEST("1006", "이미 존재하는 이메일입니다."),
    CLIENT_NOT_FOUND_RESOURCE("1001", "리소스를 찾을 수 없습니다.");

    private final String status;
    private final String message;
}
