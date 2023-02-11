package com.gdscssu.garbagecollector.domain.user;


import com.gdscssu.garbagecollector.global.BaseException;
import com.gdscssu.garbagecollector.global.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.gdscssu.garbagecollector.global.BaseResponseStatus.BAD_REQUEST;
import static com.gdscssu.garbagecollector.global.BaseResponseStatus.SUCCESS;


@RestController
public class UserController {

    //
    @GetMapping("/user")

    public ResponseEntity<BaseResponse>userTest(){

        try{
            return ResponseEntity.ok(new BaseResponse<>(SUCCESS));
        } catch(BaseException exception){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new BaseResponse<>(BAD_REQUEST));
        }

    }
}
