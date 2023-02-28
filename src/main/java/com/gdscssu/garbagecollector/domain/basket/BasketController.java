package com.gdscssu.garbagecollector.domain.basket;


import com.gdscssu.garbagecollector.domain.basket.dto.BasketRecommendDto;
import com.gdscssu.garbagecollector.domain.basket.dto.BasketReportDto;
import com.gdscssu.garbagecollector.domain.basket.dto.PostBasketMarkingReq;
import com.gdscssu.garbagecollector.domain.basket.dto.BasketModelDto;
import com.gdscssu.garbagecollector.domain.basket.entity.Basket;
import com.gdscssu.garbagecollector.domain.basket.service.BasketService;
import com.gdscssu.garbagecollector.global.config.error.BaseResponse;
import com.gdscssu.garbagecollector.global.config.security.jwt.JwtAuthenticationFilter;
import com.gdscssu.garbagecollector.global.config.security.jwt.JwtTokenProvider;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@AllArgsConstructor
public class BasketController {

    private final BasketService basketService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private final JwtTokenProvider jwtTokenProvider;

    // [MAP] /baskets
    // 유저의 위도 경도를 받아와서 소수점 다섯번째자리까지 똑같은 데이터를 찾아서 반환해줌
    @PostMapping("/baskets")
    public ResponseEntity<BaseResponse<List<BasketModelDto>>> homeBasketMarking(@RequestBody PostBasketMarkingReq postBasketMarkingReq, HttpServletRequest request){

        Double lng1= postBasketMarkingReq.getLng1();
        Double lat1=postBasketMarkingReq.getLat1();
        Double lng2=postBasketMarkingReq.getLng2();
        Double lat2=postBasketMarkingReq.getLat2();
        String jwtToken=jwtAuthenticationFilter.getJwtFromRequest(request);
        String userEmail=jwtTokenProvider.getUserEmailFromJWT(jwtToken);
        List<BasketModelDto> postBasketMarkingResList=basketService.homeBasketMarking(lng1,lat1,lng2,lat2,userEmail);

        return ResponseEntity.ok(new BaseResponse<>(postBasketMarkingResList));

    }

    @GetMapping("/baskets/{basketId}")
    public ResponseEntity<BaseResponse<BasketModelDto>> homeBasketDetail(@PathVariable("basketId")Long basketID, HttpServletRequest httpRequest){
        String jwtToken=jwtAuthenticationFilter.getJwtFromRequest(httpRequest);
        String userEmail=jwtTokenProvider.getUserEmailFromJWT(jwtToken);
        BasketModelDto basketModelDto=basketService.basketDetail(basketID,userEmail);

        return ResponseEntity.ok(new BaseResponse<>(basketModelDto));

    }

    @PostMapping("/basket/report")
    public ResponseEntity<BaseResponse<Long>> basketReport(@RequestBody BasketReportDto basketReportDto){

        Long id=basketService.basketReport(basketReportDto);


        return ResponseEntity.ok(new BaseResponse<>(id));

    }

    @PostMapping("/basket/recommend")
    public ResponseEntity<BaseResponse<List<BasketModelDto>>> basketRecommend(@RequestBody BasketRecommendDto basketRecommendDto){

        List<BasketModelDto> basketModelDtoList=basketService.basketRecommend(basketRecommendDto);


        return ResponseEntity.ok(new BaseResponse<>(basketModelDtoList));

    }


}
