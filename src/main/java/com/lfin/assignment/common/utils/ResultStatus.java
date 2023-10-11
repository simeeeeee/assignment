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
    CLIENT_NOT_CHANGE_VALUE_REQUEST("1003", "해당 값을 변경할 수 없습니다."),
    CLIENT_NOT_MATCH_VALUE_REQUEST("1005", "기존 정보와 일치하지 않습니다."),
    CLIENT_EXIST_VALUE_REQUEST("1006", "이미 존재하는 값입니다."),
    CLIENT_NOT_FOUND_RESOURCE("1001", "리소스를 찾을 수 없습니다.");

    private final String status;
    private final String message;
}
