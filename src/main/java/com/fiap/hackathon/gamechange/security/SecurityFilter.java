package com.fiap.hackathon.gamechange.security;

import com.fiap.hackathon.gamechange.OutLayer.infrastructure.persistence.IUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {
    @Autowired
    TokenService tokenService;
    @Autowired
    IUserRepository userRepository;
    @Autowired
    private AuthorizationService authorizationService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String requestURI = request.getRequestURI();
        // Ignorar o filtro de autenticação para rotas públicas
        if ("/auth/login".equals(requestURI) || "/users".equals(requestURI)) {
            filterChain.doFilter(request, response);
            return;
        }

        var token = this.recoverToken(request);
        if(token != null){
            try {
                var login = tokenService.validateToken(token);
                UserDetails user = authorizationService.loadUserByUsername(login);

                if (user != null) {
                     var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                     SecurityContextHolder.getContext().setAuthentication(authentication);
                 }
            } catch (Exception e) {
                // Log do erro ou outras ações
            }
        }
        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request){
        var authHeader = request.getHeader("Authorization");
        if(authHeader == null) return null;
        return authHeader.replace("Bearer ", "");
    }

}
