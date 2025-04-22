package com.beomju.minipj.member.service;


import com.beomju.minipj.member.dto.MemberJoinDTO;

public interface MemberService {

    String getKakaoEmail(String accessToken);
    void join(MemberJoinDTO joinDTO);
}
