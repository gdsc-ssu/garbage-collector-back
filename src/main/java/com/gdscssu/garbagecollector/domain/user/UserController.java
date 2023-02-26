package com.gdscssu.garbagecollector.domain.user;


import com.gdscssu.garbagecollector.domain.user.dto.PostLoginReq;
import com.gdscssu.garbagecollector.domain.user.dto.PostLoginRes;
import com.gdscssu.garbagecollector.domain.user.dto.TokenDto;
import com.gdscssu.garbagecollector.domain.user.dto.UserModelDto;
import com.gdscssu.garbagecollector.domain.user.service.UserService;
import com.gdscssu.garbagecollector.global.config.OAuth.google.GoogleOAuth;
import com.gdscssu.garbagecollector.global.config.OAuth.google.GoogleOAuthToken;
import com.gdscssu.garbagecollector.global.config.OAuth.google.OAuthService;
import com.gdscssu.garbagecollector.global.config.error.BaseResponse;
import com.gdscssu.garbagecollector.global.config.error.exception.BaseException;
import com.gdscssu.garbagecollector.global.config.security.jwt.JwtAuthenticationFilter;
import com.gdscssu.garbagecollector.global.config.security.jwt.JwtTokenProvider;
import com.nimbusds.jose.shaded.json.parser.ParseException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {


    private final UserService userService;
    private final OAuthService oAuthService;

    private final GoogleOAuth googleOAuth;
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @GetMapping("/test")
    public ResponseEntity<String> test(){
        return ResponseEntity.ok("hello");
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

    //구글 로그인


    @PostMapping("/login")
    public ResponseEntity<BaseResponse<PostLoginRes>> login(@RequestBody PostLoginReq postLoginReq) throws ParseException {

        PostLoginRes postLoginRes =userService.login(postLoginReq);


        return ResponseEntity.ok(new BaseResponse<>(postLoginRes));


    }

    @GetMapping("/{userId}")
    public ResponseEntity<BaseResponse<UserModelDto>>getUserInfo(@PathVariable("userId")Long userId){
        UserModelDto userModelDto=userService.getUserInfo(userId);
        return ResponseEntity.ok(new BaseResponse<>(userModelDto));
    }


}
