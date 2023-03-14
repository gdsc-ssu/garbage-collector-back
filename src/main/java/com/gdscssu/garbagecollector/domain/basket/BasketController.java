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
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@AllArgsConstructor
@Api(tags = "쓰레기통 API")
public class BasketController {

    private final BasketService basketService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private final JwtTokenProvider jwtTokenProvider;

    // [MAP] /baskets
    // 유저의 위도 경도를 받아와서 소수점 다섯번째자리까지 똑같은 데이터를 찾아서 반환해줌
    @ApiOperation(value = "Body 내 lat1,lng1와 lat2,lng2 사이 쓰레기통 데이터들")
    @PostMapping("/baskets")
    public ResponseEntity<BaseResponse<List<BasketModelDto>>> homeBasketMarking(@RequestBody PostBasketMarkingReq postBasketMarkingReq){

        Double lng1= postBasketMarkingReq.getLng1();
        Double lat1=postBasketMarkingReq.getLat1();
        Double lng2=postBasketMarkingReq.getLng2();
        Double lat2=postBasketMarkingReq.getLat2();

        List<BasketModelDto> postBasketMarkingResList=basketService.homeBasketMarking(lng1,lat1,lng2,lat2);

        return ResponseEntity.ok(new BaseResponse<>(postBasketMarkingResList));

    }
    @ApiOperation(value = "특정 쓰레기통 상세 데이터")
    @GetMapping("/baskets/{basketId}")
    public ResponseEntity<BaseResponse<BasketModelDto>> homeBasketDetail(@PathVariable("basketId")Long basketID){

        BasketModelDto basketModelDto=basketService.basketDetail(basketID);

        return ResponseEntity.ok(new BaseResponse<>(basketModelDto));

    }
    @ApiOperation(value = "쓰레기통 신고하기")
    @PostMapping("/basket/report")
    public ResponseEntity<BaseResponse<Long>> basketReport(@RequestBody BasketReportDto basketReportDto){

        Long id=basketService.basketReport(basketReportDto);


        return ResponseEntity.ok(new BaseResponse<>(id));

    }
    @ApiOperation(value = "유저 주위 쓰레기통 추천")
    @PostMapping("/basket/recommend")
    public ResponseEntity<BaseResponse<List<BasketModelDto>>> basketRecommend(@RequestBody BasketRecommendDto basketRecommendDto){

        List<BasketModelDto> basketModelDtoList=basketService.basketRecommend(basketRecommendDto);


        return ResponseEntity.ok(new BaseResponse<>(basketModelDtoList));

    }


}
