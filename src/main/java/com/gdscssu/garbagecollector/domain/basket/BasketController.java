package com.gdscssu.garbagecollector.domain.basket;


import com.gdscssu.garbagecollector.domain.basket.dto.PostBasketMarkingReq;
import com.gdscssu.garbagecollector.domain.basket.dto.BasketModelDto;
import com.gdscssu.garbagecollector.domain.basket.service.BasketService;
import com.gdscssu.garbagecollector.global.config.error.BaseResponse;
import com.gdscssu.garbagecollector.global.config.security.jwt.JwtAuthenticationFilter;
import com.gdscssu.garbagecollector.global.config.security.jwt.JwtTokenProvider;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/basket")
public class BasketController {

    private final BasketService basketService;

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private final JwtTokenProvider jwtTokenProvider;

    // [MAP] /baskets
    // 유저의 위도 경도를 받아와서 소수점 다섯번째자리까지 똑같은 데이터를 찾아서 반환해줌
    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    @ApiOperation(value = "특정 위치의 쓰레기 통 조회")
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

    @GetMapping("/nearby")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @ApiOperation(value = "근처 쓰레기 통 조회")
    public ResponseEntity<BaseResponse<List<BasketModelDto>>> listNearbyBasket(@RequestParam Double lat, @RequestParam Double lng, HttpServletRequest request) {
        String jwtToken=jwtAuthenticationFilter.getJwtFromRequest(request);
        String userEmail=jwtTokenProvider.getUserEmailFromJWT(jwtToken);
        List<BasketModelDto> baskets = basketService.listNearbyBasket(lat, lng, userEmail);

        return ResponseEntity.ok(new BaseResponse<>(baskets));
    }

    @GetMapping("/{basketId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @ApiOperation(value = "쓰레기 통 조회")
    public ResponseEntity<BaseResponse<BasketModelDto>> homeBasketDetail(@PathVariable("basketId")Long basketID, HttpServletRequest httpRequest){
        String jwtToken=jwtAuthenticationFilter.getJwtFromRequest(httpRequest);
        String userEmail=jwtTokenProvider.getUserEmailFromJWT(jwtToken);
        BasketModelDto basketModelDto=basketService.basketDetail(basketID,userEmail);

        return ResponseEntity.ok(new BaseResponse<>(basketModelDto));

    }
}
