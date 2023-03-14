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
import com.gdscssu.garbagecollector.global.config.error.ErrorCode;
import com.gdscssu.garbagecollector.global.config.error.exception.BaseException;
import com.gdscssu.garbagecollector.global.config.security.jwt.JwtAuthenticationFilter;
import com.gdscssu.garbagecollector.global.config.security.jwt.JwtTokenProvider;
import com.nimbusds.jose.shaded.json.parser.ParseException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@Api(tags = "유저 API")
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
    @GetMapping("/auth2")
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
    @ApiOperation(value = "구글 로그인")

    @PostMapping("/login")
    public ResponseEntity<BaseResponse<PostLoginRes>> login(@RequestBody PostLoginReq postLoginReq) throws ParseException {

        PostLoginRes postLoginRes =userService.login(postLoginReq);


        return ResponseEntity.ok(new BaseResponse<>(postLoginRes));


    }
    @ApiOperation(value = "유저 정보 불러오기")
    @GetMapping("/{userId}")
    public ResponseEntity<BaseResponse<UserModelDto>>getUserInfo(@PathVariable("userId")Long userId){
        UserModelDto userModelDto=userService.getUserInfo(userId);
        return ResponseEntity.ok(new BaseResponse<>(userModelDto));
    }

    // 헤더에 accessToken으로 유저정보 추출
    @ApiOperation(value = "헤더의 accessToken으로 유저정보 조회")
    @GetMapping("/auth")
    public ResponseEntity<BaseResponse<UserModelDto>>getUserInfoByAccessToken(HttpServletRequest request){
        String jwtToken=jwtAuthenticationFilter.getJwtFromRequest(request);
        String userEmail=jwtTokenProvider.getUserEmailFromJWT(jwtToken);
        UserModelDto userModelDto=userService.getUserInfoByAccessToken(userEmail,jwtToken);
        return ResponseEntity.ok(new BaseResponse<>(userModelDto));
    }


}
