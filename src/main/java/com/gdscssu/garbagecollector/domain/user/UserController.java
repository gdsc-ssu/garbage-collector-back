package com.gdscssu.garbagecollector.domain.user;


import com.gdscssu.garbagecollector.domain.user.dto.PostLoginReq;
import com.gdscssu.garbagecollector.domain.user.dto.PostUserDumpReq;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
    public ResponseEntity<BaseResponse<TokenDto>> login(@RequestBody PostLoginReq postLoginReq) throws ParseException {

        TokenDto tokenDto=userService.login(postLoginReq);

        System.out.println(tokenDto.getAccessToken());
        return ResponseEntity.ok(new BaseResponse<>(tokenDto));


    }

    // 쓰레기 버리기

    @GetMapping("/dump")
    public ResponseEntity<BaseResponse<UserModelDto>>userDump(@RequestBody PostUserDumpReq postUserDumpReq, HttpServletRequest httpServletRequest){
        String jwt=jwtAuthenticationFilter.getJwtFromRequest(httpServletRequest);
        String email=jwtTokenProvider.getUserEmailFromJWT(jwt);
        UserModelDto userModelDto=userService.userDump(postUserDumpReq,email);

        return ResponseEntity.ok(new BaseResponse<>(userModelDto));
    }
}
