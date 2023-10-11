package com.lfin.assignment.common.utils;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class ApiResult {

    private String status;
    private String message;
    private Object data;
    private long timestamp = Instant.now().toEpochMilli();

    public ApiResult(){
        Map<String, Boolean> result = new HashMap<>();
        result.put("result", true);
        this.status = ResultStatus.REQUEST_SUCCESS.getStatus();
        this.message = ResultStatus.REQUEST_SUCCESS.getMessage();
        this.data = result;
    }

    public ApiResult(Object data){
        this.status = ResultStatus.REQUEST_SUCCESS.getStatus();
        this.message = ResultStatus.REQUEST_SUCCESS.getMessage();
        this.data = data;
    }

    public ApiResult(ResultStatus status){
        this.status = status.getStatus();
        this.message = status.getMessage();
    }
}
