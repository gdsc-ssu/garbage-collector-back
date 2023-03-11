package com.gdscssu.garbagecollector.domain.trash;

import com.gdscssu.garbagecollector.domain.trash.service.TrashService;
import com.gdscssu.garbagecollector.domain.trash.dto.PostUserDumpReq;
import com.gdscssu.garbagecollector.domain.user.dto.UserModelDto;
import com.gdscssu.garbagecollector.global.config.error.BaseResponse;
import com.gdscssu.garbagecollector.global.config.security.jwt.JwtAuthenticationFilter;
import com.gdscssu.garbagecollector.global.config.security.jwt.JwtTokenProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class TrashController {
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private final TrashService trashService;

    public TrashController(JwtTokenProvider jwtTokenProvider, JwtAuthenticationFilter jwtAuthenticationFilter, TrashService trashService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.trashService = trashService;
    }
    // 쓰레기 버리기

    @PostMapping("/trash")
    public ResponseEntity<BaseResponse<UserModelDto>> userDump(@RequestBody PostUserDumpReq postUserDumpReq, HttpServletRequest httpServletRequest){
        String jwt=jwtAuthenticationFilter.getJwtFromRequest(httpServletRequest);
        String email=jwtTokenProvider.getUserEmailFromJWT(jwt);
        UserModelDto userModelDto=trashService.userDump(postUserDumpReq,email,jwt);

        return ResponseEntity.ok(new BaseResponse<>(userModelDto));
    }
}
