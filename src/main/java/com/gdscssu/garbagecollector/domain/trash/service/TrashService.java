package com.gdscssu.garbagecollector.domain.trash.service;

import com.gdscssu.garbagecollector.domain.basket.entity.Basket;
import com.gdscssu.garbagecollector.domain.basket.repository.BasketRepository;
import com.gdscssu.garbagecollector.domain.collection.entity.Collection;
import com.gdscssu.garbagecollector.domain.collection.entity.CollectionType;
import com.gdscssu.garbagecollector.domain.collection.repository.CollectionRepository;
import com.gdscssu.garbagecollector.domain.score.entity.Score;
import com.gdscssu.garbagecollector.domain.score.repository.ScoreRepository;
import com.gdscssu.garbagecollector.domain.trash.entity.Trash;
import com.gdscssu.garbagecollector.domain.trash.entity.TrashType1;
import com.gdscssu.garbagecollector.domain.trash.entity.TrashType2;
import com.gdscssu.garbagecollector.domain.trash.repository.TrashRepository;
import com.gdscssu.garbagecollector.domain.trash.dto.PostUserDumpReq;
import com.gdscssu.garbagecollector.domain.user.dto.UserModelDto;
import com.gdscssu.garbagecollector.domain.user.entity.User;
import com.gdscssu.garbagecollector.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class TrashService {

    private final BasketRepository basketRepository;
    private final UserRepository userRepository;
    private final ScoreRepository scoreRepository;

    private final TrashRepository trashRepository;

    private final CollectionRepository collectionRepository;



    public UserModelDto userDump(PostUserDumpReq postUserDumpReq, String email,String jwt){
        TrashType1 trashType1= TrashType1.valueOf(postUserDumpReq.getTrashType1());
        TrashType2 trashType2= TrashType2.valueOf(postUserDumpReq.getTrashType2());
        Optional<Basket> basket=basketRepository.findBasketById(postUserDumpReq.getBasketId());
        Optional<User> user=userRepository.findByEmail(email);
        System.out.println(trashType1.name());
        int exp=0;
        if(trashType2.equals(TrashType2.GENERAL)){
            exp=20;

        }else if(trashType2.equals(TrashType2.PAPER)){
            exp=30;
        }else if(trashType2.equals(TrashType2.CAN)){
            exp=35;
        }else if(trashType2.equals(TrashType2.GLASS)){
            exp=40;
        }else if(trashType2.equals(TrashType2.PLASTIC)){
            exp=40;
        }



        Collection collection=collectionRepository.save(Collection.builder()
               .user(user.orElseThrow(()->new RuntimeException("??????????????? ???????????? ????????????.")))
               .level(1)
               .exp(20)
               .type(CollectionType.valueOf(postUserDumpReq.getTrashType2()))
               .build());

        Trash trash= Trash.builder()
                .type1(trashType1)
                .type2(trashType2)
                .basket(basket.orElseThrow(()->new RuntimeException("??????????????? ???????????? ????????????.")))
                .user(user.orElseThrow(()-> new RuntimeException("????????? ???????????? ????????????.")))
                .collection(collection)
                .build();
        trashRepository.save(trash);

        // Score??? ??????
        Score score = new Score(user.orElseThrow(()->new RuntimeException("????????? ???????????? ????????????.")), basket.orElseThrow(()->
                new RuntimeException("??????????????? ???????????? ????????????")), collection, collection.getExp());

        Score saved = scoreRepository.save(score);

        int can= trashRepository.findCanCount(user.orElseThrow(()->new RuntimeException("????????? ???????????? ????????????.")).getId());
        int general=trashRepository.findGeneralCount(user.orElseThrow(()->new RuntimeException("????????? ???????????? ????????????.")).getId());
        int plastic=trashRepository.findPlasticCount(user.orElseThrow(()->new RuntimeException("????????? ???????????? ????????????.")).getId());
        int glass=trashRepository.findGlassCount(user.orElseThrow(()->new RuntimeException("????????? ???????????? ????????????.")).getId());
        int paper=trashRepository.findPaperCount(user.orElseThrow(()->new RuntimeException("????????? ???????????? ????????????.")).getId());


        UserModelDto userModelDto=UserModelDto.builder()
                .id(user.orElseThrow(()->new RuntimeException("????????? ???????????? ????????????.")).getId())
                .email(user.orElseThrow(()->new RuntimeException("????????? ???????????? ????????????.")).getEmail())
                .nickname(user.orElseThrow(()->new RuntimeException("????????? ???????????? ????????????.")).getNickname())
                .profileImg(user.orElseThrow(()->new RuntimeException("????????? ???????????? ????????????.")).getProfileImg())
                .can(can)
                .createdAt(user.orElseThrow().getCreatedAt())
                .updatedAt(user.orElseThrow().getUpdatedAt())
                .accessToken(jwt)
                .general(general)
                .plastic(plastic)
                .glass(glass)
                .paper(paper)
                .build();

        return userModelDto;

    }
}
