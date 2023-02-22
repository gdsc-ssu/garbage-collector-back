package com.gdscssu.garbagecollector.domain.basket.service;

import com.gdscssu.garbagecollector.domain.basket.dto.BasketModelDto;
import com.gdscssu.garbagecollector.domain.basket.entity.Basket;
import com.gdscssu.garbagecollector.domain.basket.repository.BasketRepository;
import com.gdscssu.garbagecollector.domain.basket.repository.UserBasketRepository;
import com.gdscssu.garbagecollector.domain.user.entity.User;
import com.gdscssu.garbagecollector.domain.user.repository.UserRepository;
import com.gdscssu.garbagecollector.global.config.error.ErrorCode;
import com.gdscssu.garbagecollector.global.config.error.exception.BaseException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BasketService {

    private final BasketRepository basketRepository;
    private final UserBasketRepository userBasketRepository;

    private final UserRepository userRepository;


    public BasketService(BasketRepository basketRepository, UserBasketRepository userBasketRepository, UserRepository userRepository) {
        this.basketRepository = basketRepository;

        this.userBasketRepository = userBasketRepository;
        this.userRepository = userRepository;
    }

    public List<BasketModelDto> homeBasketMarking(double lng1, double lat1, double lng2, double lat2, String userEmail){

        List<Basket> baskets= basketRepository.findBasketByLngAndLat(lng1,lat1,lng2,lat2);
        Optional<User> user =userRepository.findByEmail(userEmail);
        Long userId= 0L;
        if(user.isPresent()){
            userId=user.get().getId();
        }else{
            throw new BaseException(ErrorCode.USER_NOT_FOUND);
        }



        //System.out.println(baskets.get(0).getLocation3());
        List<BasketModelDto> postBasketMarkingList=new ArrayList<>();
        if(baskets.size()==0){
            postBasketMarkingList.add(
                    BasketModelDto.builder()
                            .basketName("")
                            .lng(0)
                            .lat(0)
                            .userTrash(0)
                            .build());


        }else{
            for(Basket basket :baskets){
                Long basketId=basket.getId();

                int userTrash=userBasketRepository.UserBasketCount(userId,basketId);
                postBasketMarkingList.add(
                        BasketModelDto.builder()
                                .basketId(basket.getId())
                                .basketName(basket.getLocation3())
                                .lng(basket.getLng())
                                .lat(basket.getLat())
                                .userTrash(userTrash)
                                .updatedAt(basket.getUpdatedAt())
                                .build());
            }
        }

        return postBasketMarkingList;
    }

    public BasketModelDto basketDetail(Long basketId,String userEmail){
        Optional<Basket> basket=basketRepository.findBasketById(basketId);
        Optional<User> user =userRepository.findByEmail(userEmail);
        Long userId= 0L;
        if(user.isPresent()){
            userId=user.get().getId();
        }else{
            throw new BaseException(ErrorCode.USER_NOT_FOUND);
        }

        if(basket.isPresent()){
            int userTrash=userBasketRepository.UserBasketCount(userId,basketId);
            return BasketModelDto.builder()
                    .basketName(basket.get().getLocation3())
                    .basketId(basketId)
                    .userTrash(userTrash)
                    .lat(basket.get().getLat())
                    .lng(basket.get().getLng())
                    .updatedAt(basket.get().getUpdatedAt())
                    .build();


        }else{
            throw new BaseException(ErrorCode.BASKET_NOT_FOUND);

        }

    }


}
