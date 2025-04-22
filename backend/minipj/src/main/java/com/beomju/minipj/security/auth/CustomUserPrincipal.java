package com.beomju.minipj.security.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@RequiredArgsConstructor
public class CustomUserPrincipal implements Principal {

    private final String uid;

    @Override
    public String getName() {
        return uid;
    }
}