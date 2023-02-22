package com.gdscssu.garbagecollector.domain.basket;


import com.gdscssu.garbagecollector.domain.basket.dto.PostBasketMarkingReq;
import com.gdscssu.garbagecollector.domain.basket.dto.PostBasketMarkingRes;
import com.gdscssu.garbagecollector.domain.basket.service.BasketService;
import com.gdscssu.garbagecollector.global.config.error.BaseResponse;
import com.gdscssu.garbagecollector.global.config.security.jwt.JwtAuthenticationFilter;
import com.gdscssu.garbagecollector.global.config.security.jwt.JwtTokenProvider;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@AllArgsConstructor
public class BasketController {

    private final BasketService basketService;

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private final JwtTokenProvider jwtTokenProvider;

    // [MAP] /baskets
    // 유저의 위도 경도를 받아와서 소수점 다섯번째자리까지 똑같은 데이터를 찾아서 반환해줌
    @PostMapping("/baskets")
    public ResponseEntity<BaseResponse<List<PostBasketMarkingRes>>> homeBasketMarking(@RequestBody PostBasketMarkingReq postBasketMarkingReq, HttpServletRequest request){

        Double lng1= postBasketMarkingReq.getLng1();
        Double lat1=postBasketMarkingReq.getLat1();
        Double lng2=postBasketMarkingReq.getLng2();
        Double lat2=postBasketMarkingReq.getLat2();
        String jwtToken=jwtAuthenticationFilter.getJwtFromRequest(request);
        String userEmail=jwtTokenProvider.getUserEmailFromJWT(jwtToken);
        List<PostBasketMarkingRes> postBasketMarkingResList=basketService.homeBasketMarking(lng1,lat1,lng2,lat2,userEmail);

        return ResponseEntity.ok(new BaseResponse<>(postBasketMarkingResList));

    }
}
