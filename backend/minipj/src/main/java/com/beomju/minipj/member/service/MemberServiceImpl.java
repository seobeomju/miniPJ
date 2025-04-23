package com.beomju.minipj.member.service;

import com.beomju.minipj.member.dto.MemberJoinDTO;
import com.beomju.minipj.member.entities.Member;
import com.beomju.minipj.member.entities.MemberRole;
import com.beomju.minipj.member.repoitory.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@Transactional
@Service
@Log4j2
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public String getKakaoEmail(String accessToken) {

        String kakaoGetUserURL = "https://kapi.kakao.com/v2/user/me";

        if(accessToken == null){
            throw new RuntimeException("Access Token is null");
        }
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-Type","application/x-www-form-urlencoded");

        HttpEntity<String> entity = new HttpEntity<>(headers);

        UriComponents uriBuilder = UriComponentsBuilder.fromHttpUrl(kakaoGetUserURL).build();

        ResponseEntity<LinkedHashMap> response =
                restTemplate.exchange(
                        uriBuilder.toString(),
                        HttpMethod.GET,
                        entity,
                        LinkedHashMap.class);

        log.info(response);

        LinkedHashMap<String, LinkedHashMap> bodyMap = response.getBody();

        log.info("------------------------------------");
        log.info(bodyMap);

        LinkedHashMap<String, String> kakaoAccount = bodyMap.get("kakao_account");

        log.info("kakaoAccount: " + kakaoAccount);

        return kakaoAccount.get("email");
    }

    @Transactional
    @Override
    public void join(MemberJoinDTO joinDTO) {

        Optional<Member> result = memberRepository.findById(joinDTO.getMid());

        if(result.isPresent()){
            throw new RuntimeException("이미 존재하는 아이디입니다");
        }
        Member member = Member.builder()
                .mid(joinDTO.getMid())
                .mpw(passwordEncoder.encode(joinDTO.getMpw()))
                .email(joinDTO.getEmail())
                .del(false)
                .social(false)
                .build();

        member.addRole(MemberRole.USER);

        memberRepository.save(member);
    }

    @Override
    public Member registerSocialMemberIfNotExists(String email) {
        Member member = memberRepository.findById(email).orElse(null);


        if (member == null) {
            member = Member.builder()
                    .mid(email)
                    .mpw("Social")
                    .email(email)
                    .social(true)
                    .del(false)
                    .build();
            member.addRole(MemberRole.USER);

            memberRepository.save(member);
        }

        return member;
    }
}