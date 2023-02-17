package com.gdscssu.garbagecollector.domain.basket.service;

import com.gdscssu.garbagecollector.domain.basket.dto.PostBasketMarkingRes;
import com.gdscssu.garbagecollector.domain.basket.entity.Basket;
import com.gdscssu.garbagecollector.domain.basket.repository.BasketRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class BasketService {

    private final BasketRepository basketRepository;

    public BasketService(BasketRepository basketRepository) {
        this.basketRepository = basketRepository;
    }

    public List<PostBasketMarkingRes> homeBasketMarking(String lng,String lat){

        List<Basket> baskets= basketRepository.findBasketByLngAndLat(lng,lat);

        //System.out.println(baskets.get(0).getLocation3());
        List<PostBasketMarkingRes> postBasketMarkingList=new ArrayList<>();
        if(baskets.size()==0){
            postBasketMarkingList.add(
                    PostBasketMarkingRes.builder()
                            .basketName("")
                            .lng("")
                            .lat("")
                            .userTrash(0)
                            .build());


        }else{
            for(Basket basket :baskets){
                postBasketMarkingList.add(
                        PostBasketMarkingRes.builder()
                                .basketName(basket.getLocation3())
                                .lng(basket.getLng().toString())
                                .lat(basket.getLat().toString())
                                .userTrash(3)
                                .build());
            }
        }

        return postBasketMarkingList;
    }


}
