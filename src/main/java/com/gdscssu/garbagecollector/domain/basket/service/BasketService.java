package com.gdscssu.garbagecollector.domain.basket.service;

import com.gdscssu.garbagecollector.domain.basket.dto.BasketModelDto;
import com.gdscssu.garbagecollector.domain.basket.dto.BasketRecommendDto;
import com.gdscssu.garbagecollector.domain.basket.dto.BasketReportDto;
import com.gdscssu.garbagecollector.domain.basket.entity.Basket;
import com.gdscssu.garbagecollector.domain.basket.entity.BasketType;
import com.gdscssu.garbagecollector.domain.basket.entity.Report;
import com.gdscssu.garbagecollector.domain.basket.entity.ReportType;
import com.gdscssu.garbagecollector.domain.basket.repository.BasketReportRepository;
import com.gdscssu.garbagecollector.domain.basket.repository.BasketRepository;
import com.gdscssu.garbagecollector.domain.trash.repository.TrashRepository;
import com.gdscssu.garbagecollector.domain.user.entity.User;
import com.gdscssu.garbagecollector.domain.user.repository.UserRepository;
import com.gdscssu.garbagecollector.global.config.error.ErrorCode;
import com.gdscssu.garbagecollector.global.config.error.exception.BaseException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class BasketService {

    private final BasketRepository basketRepository;
    private final TrashRepository trashRepository;
    private final BasketReportRepository basketReportRepository;

    private final UserRepository userRepository;


    public BasketService(BasketRepository basketRepository, TrashRepository trashRepository, BasketReportRepository basketReportRepository, UserRepository userRepository) {
        this.basketRepository = basketRepository;
        this.trashRepository = trashRepository;
        this.basketReportRepository = basketReportRepository;
        this.userRepository = userRepository;
    }

    public List<BasketModelDto> homeBasketMarking(double lng1, double lat1, double lng2, double lat2){

        List<Basket> baskets= basketRepository.findBasketByLngAndLat(lng1,lat1,lng2,lat2);



        //System.out.println(baskets.get(0).getLocation3());
        List<BasketModelDto> postBasketMarkingList=new ArrayList<>();
        if(baskets.size()==0){
            postBasketMarkingList.add(
                    BasketModelDto.builder()
                            .basketName("")
                            .lng(0)
                            .lat(0)
                            .build());


        }else{
            for(Basket basket :baskets){
                Long basketId=basket.getId();


                postBasketMarkingList.add(
                        BasketModelDto.builder()
                                .id(basket.getId())
                                .basketName(basket.getLocation3())
                                .lng(basket.getLng())
                                .lat(basket.getLat())
                                .detailAddress(basket.getDetailAddress())
                                .updatedAt(basket.getUpdatedAt())
                                .build());
            }
        }

        return postBasketMarkingList;
    }

    public BasketModelDto basketDetail(Long basketId){
        Optional<Basket> basket=basketRepository.findBasketById(basketId);



        if(basket.isPresent()){

            return BasketModelDto.builder()
                    .basketName(basket.get().getLocation3())
                    .id(basketId)
                    .detailAddress(basket.get().getDetailAddress())
                    .lat(basket.get().getLat())
                    .lng(basket.get().getLng())
                    .updatedAt(basket.get().getUpdatedAt())
                    .build();


        }else{
            throw new BaseException(ErrorCode.BASKET_NOT_FOUND);

        }

    }

    // 인덱스 앞에 explain 써놓기

    // abs(현재위도 경도-쓰레기통 위도 경도) order by로 (점과 점 사이의 거리) 위도,경도 index s

    public List<BasketModelDto> basketRecommend(BasketRecommendDto basketRecommendDto){
        Double lng= basketRecommendDto.getLng();
        Double lat= basketRecommendDto.getLat();
        String type=basketRecommendDto.getType();
        List<Basket> basketList= basketRepository.basketRecommendList(lng,lat,type);
        List<BasketModelDto> basketModelDtoList=basketList.stream().map(
                basket -> BasketModelDto.builder()
                        .basketName(basket.getDetailAddress())
                        .lat(basket.getLat())
                        .lng(basket.getLng())
                        .detailAddress(basket.getLocation3())
                        .id(basket.getId())
                        .updatedAt(basket.getUpdatedAt())
                        .build()
        ).collect(Collectors.toList());
        return  basketModelDtoList;

    }

    public Long basketReport(BasketReportDto basketReportDto){
        ReportType reportType= ReportType.valueOf(basketReportDto.getReportType());
        Optional<Basket> basket=basketRepository.findBasketById(basketReportDto.getBasketId());
        Report report=basketReportRepository.save(Report.builder()
                .reportType(reportType)
                .basket(basket.orElseThrow(()->new RuntimeException("쓰레기통이 존재하지 않습니다.")))
                .build());
        return report.getId();
    }


}
