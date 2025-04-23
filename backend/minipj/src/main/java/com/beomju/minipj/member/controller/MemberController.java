package com.beomju.minipj.member.controller;

import com.beomju.minipj.member.dto.MemberDTO;
import com.beomju.minipj.member.dto.MemberJoinDTO;
import com.beomju.minipj.member.entities.Member;
import com.beomju.minipj.member.repoitory.MemberRepository;
import com.beomju.minipj.member.service.MemberService;
import com.beomju.minipj.util.JWTException;
import com.beomju.minipj.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


import java.util.Map;

@RestController
@Log4j2
@RequiredArgsConstructor
public class MemberController {

   private final MemberService memberService;

    private final JWTUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    @PostMapping("/api/v1/member/login")
    public ResponseEntity<String[]> login (@RequestParam("uid") String uid,@RequestParam("upw") String upw) {

        log.info("login----------------------------");
        log.info(uid + " " + upw);

        Member member = memberRepository.selectOne(uid);

        if(member == null || passwordEncoder.matches(upw, member.getMpw())==false) {
            //잘못된 로그인 정보
            throw new JWTException("Invalid password");
        }

        String accessToken = jwtUtil.createToken(Map.of("uid",uid), 5);
        String refreshToken = jwtUtil.createToken(Map.of("uid",uid), 10); //60*24*7

        String[] result = new String[]{accessToken, refreshToken};

        return ResponseEntity.ok(result);
    }


    @GetMapping("/api/v1/member/kakao")
    public ResponseEntity<String[]> getKakao( @RequestParam("accessToken") String accessToken) {

        log.info("getKakao: " + accessToken);

        //카카오 이메일 받아오기
        String kakaoEmail = memberService.getKakaoEmail(accessToken);
        log.info("kakaoEmail: " + kakaoEmail);


        Member member = memberService.registerSocialMemberIfNotExists(kakaoEmail);

        String accessJWT = jwtUtil.createToken(Map.of("uid",kakaoEmail), 5);
        String refreshJWT = jwtUtil.createToken(Map.of("uid",kakaoEmail), 10);

        String[] result = new String[]{accessJWT, refreshJWT};

        return ResponseEntity.ok(result);
    }

    @RequestMapping("/api/v1/member/refresh")
    public ResponseEntity<String[]> refresh (
            @RequestHeader("Authorization") String accessTokenStr,
            @RequestParam("refreshToken") String refreshToken) {

        String accessToken = accessTokenStr.substring(7);
        String uid = "user00";

        try {
            jwtUtil.validateToken(refreshToken);
        }catch(Exception e){
            log.error("refresh token validation failed", e);

            throw new JWTException(e.getMessage());
        }

        String newAccessToken = jwtUtil.createToken(Map.of("uid",uid), 5);
        String newRefreshToken = jwtUtil.createToken(Map.of("uid",uid), 10); //60*24*7

        String[] result = new String[]{newAccessToken, newRefreshToken};

        return ResponseEntity.ok(result);
    }


    @PostMapping("/api/v1/member/join")
    public ResponseEntity<String> join (@RequestBody MemberJoinDTO joinDTO ) {

        log.info("join: " + joinDTO);
        memberService.join(joinDTO);

        return  ResponseEntity.ok("success");
    }
}