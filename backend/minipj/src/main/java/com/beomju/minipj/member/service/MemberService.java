package com.beomju.minipj.member.service;


import com.beomju.minipj.member.dto.MemberJoinDTO;
import com.beomju.minipj.member.entities.Member;

public interface MemberService {

    String getKakaoEmail(String accessToken);

    void join(MemberJoinDTO joinDTO);

    public Member registerSocialMemberIfNotExists(String email);
}
