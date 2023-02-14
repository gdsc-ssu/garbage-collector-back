package com.gdscssu.garbagecollector.domain.user.service;

import com.gdscssu.garbagecollector.domain.user.dto.PostLoginReq;
import com.gdscssu.garbagecollector.domain.user.dto.TokenDto;
import com.gdscssu.garbagecollector.domain.user.entity.User;
import com.gdscssu.garbagecollector.domain.user.repository.UserRepository;
import com.gdscssu.garbagecollector.global.config.BaseException;
import com.gdscssu.garbagecollector.global.config.BaseResponseStatus;
import com.gdscssu.garbagecollector.global.config.OAuth.google.GoogleOAuth;
import com.gdscssu.garbagecollector.global.config.OAuth.google.GoogleUser;
import com.gdscssu.garbagecollector.global.config.security.jwt.JwtTokenProvider;
import com.nimbusds.jose.shaded.json.JSONObject;
import com.nimbusds.jose.shaded.json.parser.JSONParser;
import com.nimbusds.jose.shaded.json.parser.ParseException;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;

    private final GoogleOAuth googleOAuth;


    public TokenDto login(PostLoginReq postLoginReq) throws BaseException, ParseException {
        // 프론트에서 받아온 유저정보와 구글 서버로 부터 요청한 정보가 일치하는지 확인한다.
        // 유저 id로 인증절차 진행 (sub)
        //액세스 토큰을 다시 구글로 보내 구글에 저장된 사용자 정보가 담긴 응답 객체를 받아온다.
        ResponseEntity<String>res = googleOAuth.requestUserInfo(postLoginReq.getAccessToken());
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(res.getBody());
        String id= (String) jsonObject.get("id");

        if(googleOAuth.validateGoogleAccessToken(postLoginReq.getAccessToken()).equals(id)){
            if(userRepository.findByEmail(postLoginReq.getUserEmail()).isEmpty()){
                System.out.println("**");
                User user = User.builder().
                        email(postLoginReq.getUserEmail())
                        .nickname(postLoginReq.getUserName())
                        .profileImg(postLoginReq.getUserProfileImg())
                        .build();

                userRepository.save(user);

            }

            return  jwtTokenProvider.createToken(postLoginReq.getUserEmail(),postLoginReq.getUserName());
        }
        throw new BaseException(BaseResponseStatus.FAIL_LOGIN);



    }
}
