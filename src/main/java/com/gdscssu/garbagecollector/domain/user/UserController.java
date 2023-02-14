package com.gdscssu.garbagecollector.domain.user;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.gdscssu.garbagecollector.domain.user.dto.PostLoginReq;
import com.gdscssu.garbagecollector.domain.user.dto.TokenDto;
import com.gdscssu.garbagecollector.domain.user.service.UserService;
import com.gdscssu.garbagecollector.global.config.BaseException;
import com.gdscssu.garbagecollector.global.config.BaseResponse;
import com.gdscssu.garbagecollector.global.config.BaseResponseStatus;
import com.gdscssu.garbagecollector.global.config.OAuth.google.GoogleOAuth;
import com.gdscssu.garbagecollector.global.config.OAuth.google.GoogleOAuthToken;
import com.gdscssu.garbagecollector.global.config.OAuth.google.GoogleUser;
import com.gdscssu.garbagecollector.global.config.OAuth.google.OAuthService;
import com.nimbusds.jose.shaded.json.parser.ParseException;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

import static com.gdscssu.garbagecollector.global.config.BaseResponseStatus.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {


    private final UserService userService;
    private final OAuthService oAuthService;

    private final GoogleOAuth googleOAuth;

    //
    @GetMapping("/test")

    public ResponseEntity<BaseResponse>userTest(){

        try{
            return ResponseEntity.ok(new BaseResponse<>(SUCCESS));
        } catch(BaseException exception){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new BaseResponse<>(FORBIDDEN));
        }

    }

    // OAuth test
    @GetMapping("/auth")
    public void socialLoginRedirect() throws IOException {
        System.out.println("auth");
        oAuthService.request();
    }

    @GetMapping("/auth/access")
    public ResponseEntity<BaseResponse<GoogleOAuthToken>> callback(@RequestParam(name="code")String code) throws IOException, BaseException, ParseException {

        GoogleOAuthToken googleOAuthToken=oAuthService.oAuthLogin(code);
        return ResponseEntity.ok(new BaseResponse<>(googleOAuthToken));
    }

    @PostMapping("/login")
    public ResponseEntity<BaseResponse<TokenDto>> login(@RequestBody PostLoginReq postLoginReq){

        try{

            TokenDto tokenDto=userService.login(postLoginReq);

            System.out.println(tokenDto.getAccessToken());
            return ResponseEntity.ok(new BaseResponse<>(tokenDto));
        }catch (BaseException exception){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BaseResponse<>(exception.getStatus()));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }


    }
}
