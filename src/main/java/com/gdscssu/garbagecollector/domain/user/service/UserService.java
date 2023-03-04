package com.gdscssu.garbagecollector.domain.user.service;

import com.gdscssu.garbagecollector.domain.basket.repository.BasketRepository;
import com.gdscssu.garbagecollector.domain.trash.repository.TrashRepository;
import com.gdscssu.garbagecollector.domain.user.dto.PostLoginReq;
import com.gdscssu.garbagecollector.domain.user.dto.PostLoginRes;
import com.gdscssu.garbagecollector.domain.user.dto.TokenDto;
import com.gdscssu.garbagecollector.domain.user.dto.UserModelDto;
import com.gdscssu.garbagecollector.domain.user.entity.User;
import com.gdscssu.garbagecollector.domain.user.repository.UserRepository;
import com.gdscssu.garbagecollector.global.config.OAuth.google.GoogleOAuth;
import com.gdscssu.garbagecollector.global.config.error.ErrorCode;
import com.gdscssu.garbagecollector.global.config.error.exception.BaseException;
import com.gdscssu.garbagecollector.global.config.security.jwt.JwtTokenProvider;
import com.nimbusds.jose.shaded.json.JSONObject;
import com.nimbusds.jose.shaded.json.parser.JSONParser;
import com.nimbusds.jose.shaded.json.parser.ParseException;
import lombok.RequiredArgsConstructor;
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
    private final TrashRepository trashRepository;
    private final BasketRepository basketRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;

    private final GoogleOAuth googleOAuth;


    public PostLoginRes login(PostLoginReq postLoginReq) throws BaseException, ParseException {
        // 프론트에서 받아온 유저정보와 구글 서버로 부터 요청한 정보가 일치하는지 확인한다.
        // 유저 id로 인증절차 진행 (sub)
        // 액세스 토큰을 다시 구글로 보내 구글에 저장된 사용자 정보가 담긴 응답 객체를 받아온다.
        ResponseEntity<String>res = googleOAuth.requestUserInfo(postLoginReq.getAccessToken());
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(res.getBody());
        String id= (String) jsonObject.get("id");

        if(googleOAuth.validateGoogleAccessToken(postLoginReq.getAccessToken()).equals(id)){
            if(userRepository.findByEmail(postLoginReq.getEmail()).isEmpty()){

                User user = User.builder().
                        email(postLoginReq.getEmail())
                        .nickname(postLoginReq.getNickName())
                        .profileImg(postLoginReq.getProfileImg())
                        .build();

                userRepository.save(user);

            }
            Authentication authentication = new UsernamePasswordAuthenticationToken(postLoginReq.getEmail(),null,null);

            TokenDto tokenDto=  jwtTokenProvider.generateToken(authentication);

            Optional<User>user=userRepository.findByEmail(postLoginReq.getEmail());
            int can= trashRepository.findCanCount(user.orElseThrow(()->new RuntimeException("유저가 존재하지 않습니다.")).getId());
            int general=trashRepository.findGeneralCount(user.orElseThrow(()->new RuntimeException("유저가 존재하지 않습니다.")).getId());
            int plastic=trashRepository.findPlasticCount(user.orElseThrow(()->new RuntimeException("유저가 존재하지 않습니다.")).getId());
            int glass=trashRepository.findGlassCount(user.orElseThrow(()->new RuntimeException("유저가 존재하지 않습니다.")).getId());
            int paper=trashRepository.findPaperCount(user.orElseThrow(()->new RuntimeException("유저가 존재하지 않습니다.")).getId());

            PostLoginRes postLoginRes=PostLoginRes.builder()
                    .id(user.orElseThrow(()->new RuntimeException("유저가 존재하지 않습니다.")).getId())
                    .email(user.orElseThrow(()->new RuntimeException("유저가 존재하지 않습니다.")).getEmail())
                    .nickname(user.orElseThrow(()->new RuntimeException("유저가 존재하지 않습니다.")).getNickname())
                    .profileImg(user.orElseThrow(()->new RuntimeException("유저가 존재하지 않습니다.")).getProfileImg())
                    .can(can)
                    .general(general)
                    .plastic(plastic)
                    .glass(glass)
                    .paper(paper)
                    .accessToken(tokenDto.getAccessToken())
                    .refreshToken(tokenDto.getRefreshToken())
                    .createdAt(user.orElseThrow(()->new RuntimeException("유저가 존재하지 않습니다.")).getCreatedAt())
                    .updatedAt(user.orElseThrow(()->new RuntimeException("유저가 존재하지 않습니다.")).getUpdatedAt())
                    .build();

            return postLoginRes;
        }
        throw new BaseException(ErrorCode.FAIL_LOGIN);



    }

    public UserModelDto getUserInfo(Long userId){
        Optional<User>user=userRepository.findById(userId);
        int can= trashRepository.findCanCount(user.orElseThrow(()->new RuntimeException("유저가 존재하지 않습니다.")).getId());
        int general=trashRepository.findGeneralCount(user.orElseThrow(()->new RuntimeException("유저가 존재하지 않습니다.")).getId());
        int plastic=trashRepository.findPlasticCount(user.orElseThrow(()->new RuntimeException("유저가 존재하지 않습니다.")).getId());
        int glass=trashRepository.findGlassCount(user.orElseThrow(()->new RuntimeException("유저가 존재하지 않습니다.")).getId());
        int paper=trashRepository.findPaperCount(user.orElseThrow(()->new RuntimeException("유저가 존재하지 않습니다.")).getId());

        UserModelDto userModelDto=UserModelDto.builder()
                .id(user.orElseThrow(()->new RuntimeException("유저가 존재하지 않습니다.")).getId())
                .email(user.orElseThrow(()->new RuntimeException("유저가 존재하지 않습니다.")).getEmail())
                .nickname(user.orElseThrow(()->new RuntimeException("유저가 존재하지 않습니다.")).getNickname())
                .profileUrl(user.orElseThrow(()->new RuntimeException("유저가 존재하지 않습니다.")).getProfileImg())
                .can(can)
                .general(general)
                .plastic(plastic)
                .glass(glass)
                .paper(paper)
                .createdAt(user.orElseThrow(()->new RuntimeException("유저가 존재하지 않습니다.")).getCreatedAt())
                .updatedAt(user.orElseThrow(()->new RuntimeException("유저가 존재하지 않습니다.")).getUpdatedAt())
                .build();

        return userModelDto;

    }

    public UserModelDto getUserInfoByAccessToken(String email){
        Optional<User>user=userRepository.findByEmail(email);
        int can= trashRepository.findCanCount(user.orElseThrow(()->new RuntimeException("유저가 존재하지 않습니다.")).getId());
        int general=trashRepository.findGeneralCount(user.orElseThrow(()->new RuntimeException("유저가 존재하지 않습니다.")).getId());
        int plastic=trashRepository.findPlasticCount(user.orElseThrow(()->new RuntimeException("유저가 존재하지 않습니다.")).getId());
        int glass=trashRepository.findGlassCount(user.orElseThrow(()->new RuntimeException("유저가 존재하지 않습니다.")).getId());
        int paper=trashRepository.findPaperCount(user.orElseThrow(()->new RuntimeException("유저가 존재하지 않습니다.")).getId());

        UserModelDto userModelDto=UserModelDto.builder()
                .id(user.orElseThrow(()->new RuntimeException("유저가 존재하지 않습니다.")).getId())
                .email(user.orElseThrow(()->new RuntimeException("유저가 존재하지 않습니다.")).getEmail())
                .nickname(user.orElseThrow(()->new RuntimeException("유저가 존재하지 않습니다.")).getNickname())
                .profileUrl(user.orElseThrow(()->new RuntimeException("유저가 존재하지 않습니다.")).getProfileImg())
                .can(can)
                .general(general)
                .plastic(plastic)
                .glass(glass)
                .paper(paper)
                .createdAt(user.orElseThrow(()->new RuntimeException("유저가 존재하지 않습니다.")).getCreatedAt())
                .updatedAt(user.orElseThrow(()->new RuntimeException("유저가 존재하지 않습니다.")).getUpdatedAt())
                .build();

        return userModelDto;


    }


}
