package com.gdscssu.garbagecollector.domain.basket;


import com.gdscssu.garbagecollector.domain.basket.dto.PostBasketMarkingReq;
import com.gdscssu.garbagecollector.domain.basket.dto.PostBasketMarkingRes;
import com.gdscssu.garbagecollector.domain.basket.service.BasketService;
import com.gdscssu.garbagecollector.global.config.BaseResponse;
import com.gdscssu.garbagecollector.global.config.BaseResponseStatus;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@AllArgsConstructor
public class BasketController {

    private final BasketService basketService;

    // [HOME] /home/basket
    // 유저의 위도 경도를 받아와서 소수점 다섯번째자리까지 똑같은 데이터를 찾아서 반환해줌
    @PostMapping("/home/basket")
    public ResponseEntity<BaseResponse<List<PostBasketMarkingRes>>> homeBasketMarking(@RequestBody PostBasketMarkingReq postBasketMarkingReq){

        try{
            String lng= postBasketMarkingReq.getLng();
            String lat= postBasketMarkingReq.getLat();
            List<PostBasketMarkingRes> postBasketMarkingResList=basketService.homeBasketMarking(lng,lat);

            return ResponseEntity.ok(new BaseResponse<>(postBasketMarkingResList));

        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new BaseResponse<>(BaseResponseStatus.NOT_FOUND));
        }


    }
}
