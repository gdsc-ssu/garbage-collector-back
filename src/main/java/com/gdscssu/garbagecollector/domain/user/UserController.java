package com.gdscssu.garbagecollector.domain.user;


import com.gdscssu.garbagecollector.domain.user.dto.PostLoginReq;
import com.gdscssu.garbagecollector.domain.user.dto.TokenDto;
import com.gdscssu.garbagecollector.domain.user.service.UserService;
import com.gdscssu.garbagecollector.global.config.OAuth.google.GoogleOAuth;
import com.gdscssu.garbagecollector.global.config.OAuth.google.GoogleOAuthToken;
import com.gdscssu.garbagecollector.global.config.OAuth.google.OAuthService;
import com.gdscssu.garbagecollector.global.config.error.BaseResponse;
import com.gdscssu.garbagecollector.global.config.error.exception.BaseException;
import com.nimbusds.jose.shaded.json.parser.ParseException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@Api(tags = "유저 및 로그인 API")
public class UserController {
    private final UserService userService;
    private final OAuthService oAuthService;

    private final GoogleOAuth googleOAuth;


    // OAuth test
    @GetMapping("/auth")
    @ApiOperation(value = "테스트테스트")
    public void socialLoginRedirect() throws IOException {
        System.out.println("auth");
        oAuthService.request();
    }

    @GetMapping("/auth/access")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @ApiOperation(value = "OAuth 토큰 반환")
    public ResponseEntity<BaseResponse<GoogleOAuthToken>> callback(@RequestParam(name="code")String code) throws IOException, BaseException, ParseException {

        GoogleOAuthToken googleOAuthToken=oAuthService.oAuthLogin(code);
        return ResponseEntity.ok(new BaseResponse<>(googleOAuthToken));
    }

    //구글 로그인


    @PostMapping("/login")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "가비지 컬렉터 로그인")
    public ResponseEntity<BaseResponse<TokenDto>> login(@RequestBody PostLoginReq postLoginReq) throws ParseException {

        TokenDto tokenDto=userService.login(postLoginReq);

        System.out.println(tokenDto.getAccessToken());
        return ResponseEntity.ok(new BaseResponse<>(tokenDto));
    }
}
