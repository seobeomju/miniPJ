package com.beomju.minipj.security.service;

import com.beomju.minipj.member.dto.MemberDTO;
import com.beomju.minipj.member.entities.Member;
import com.beomju.minipj.member.repoitory.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@Log4j2
@RequiredArgsConstructor
public class CustomUserDetailsService  implements UserDetailsService {

    private final MemberRepository memberRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        log.info("loadUserByUsername: " + username);

        Member member = memberRepo.selectOne(username);

        if(member == null) {
            throw new UsernameNotFoundException(username);
        }

        MemberDTO memberDTO = new MemberDTO(member);

        return memberDTO;
    }
}