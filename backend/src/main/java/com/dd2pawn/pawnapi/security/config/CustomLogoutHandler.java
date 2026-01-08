package com.dd2pawn.pawnapi.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import com.dd2pawn.pawnapi.model.Token;
import com.dd2pawn.pawnapi.repository.TokenRepository;
import com.dd2pawn.pawnapi.util.CookieUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
public class CustomLogoutHandler implements LogoutHandler {

    private final TokenRepository tokenRepository;
    private final CookieUtil cookieUtil;

    public CustomLogoutHandler(TokenRepository tokenRepository, CookieUtil cookieUtil) {
        this.tokenRepository = tokenRepository;
        this.cookieUtil = cookieUtil;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String token = null;

        token = cookieUtil.extractTokenFromCookie(request.getCookies(), "accessToken");

        if (token == null) {
            String authHeader = request.getHeader("Authorization");
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                token = authHeader.substring(7);
            }
        }
        if (token != null) {
            Token storedToken = tokenRepository.findByAccessToken(token).orElse(null);
            if (storedToken != null) {
                storedToken.setLoggedOut(true);
                tokenRepository.save(storedToken);
            }
        }
        cookieUtil.clearAuthCookies(response);
    }
}
