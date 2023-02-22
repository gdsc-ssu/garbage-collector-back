package com.gdscssu.garbagecollector.global.config.error;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.gdscssu.garbagecollector.global.config.error.exception.BaseException;
import com.gdscssu.garbagecollector.global.config.error.exception.JwtException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static com.gdscssu.garbagecollector.global.config.error.ErrorCode.SUCCESS;

@Getter
@AllArgsConstructor
@JsonPropertyOrder({ "code", "message", "result"})
public class BaseResponse<T>  {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private  String message;

    private final int code;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T result;

    //성공 사
    public BaseResponse(T result) {

        this.code=SUCCESS.getCode();
        this.result = result;
    }

    public BaseResponse(JwtException e) {

        this.code=e.getCode();
        this.message = e.getMessage();
    }

    public BaseResponse(ErrorCode errorCode) {
        this.code=errorCode.getCode();
        this.message = errorCode.getMessage();
    }



    public BaseResponse(BaseException e) {

        this.code=e.getCode();
        this.message=e.getMessage();
    }


}