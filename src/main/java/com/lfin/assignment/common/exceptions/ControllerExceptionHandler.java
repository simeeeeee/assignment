package com.lfin.assignment.common.exceptions;

import com.lfin.assignment.common.utils.ApiResult;
import com.lfin.assignment.common.utils.ResultStatus;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

@RestControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ApiResult resourceNotFoundException() {
        return new ApiResult(ResultStatus.CLIENT_NOT_FOUND_RESOURCE);
    }
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ApiResult sqlIntegrityConstraintViolationException() {
        return new ApiResult(ResultStatus.CLIENT_INVALID_REQUEST);
    }
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ApiResult illegalArgumentException() {
        return new ApiResult(ResultStatus.CLIENT_INVALID_REQUEST);
    }

    @ExceptionHandler(NotChangingEmailException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ApiResult notChangingValueException() {
        return new ApiResult(ResultStatus.CLIENT_NOT_CHANGE_EMAIL_REQUEST);
    }

    @ExceptionHandler(NotMatchingPasswordException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ApiResult notMatchingValueException() {
        return new ApiResult(ResultStatus.CLIENT_NOT_MATCH_PASSWORD_REQUEST);
    }
    @ExceptionHandler(ExistingEmailException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ApiResult existingValueException() {

        return new ApiResult(ResultStatus.CLIENT_EXIST_EMAIL_REQUEST);
    }
    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResult internalServerErrorException(){
        return new ApiResult(ResultStatus.INTERNAL_SERVER_ERROR);
    }
}
