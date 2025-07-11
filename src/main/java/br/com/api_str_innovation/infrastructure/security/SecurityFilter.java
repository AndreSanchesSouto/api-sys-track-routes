package br.com.api_str_innovation.infrastructure.security;

import br.com.api_str_innovation.exceptions.TokenException;
import br.com.api_str_innovation.repository.UserRepository;
import br.com.api_str_innovation.service.TokenService;
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
import java.util.UUID;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    TokenService tokenService;

    @Autowired
    UserRepository repository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException, NullPointerException {
        var token = this.recoverToken(request);
        if(token != null) {
            var id = tokenService.validateToken(token);
            try {
                UserDetails user = findById(id);
                var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (NullPointerException exception) {
                System.out.printf("The token: %s expired%n", token);
            }
        }
        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if(authHeader == null) return null;
        return authHeader.replace("Bearer ", "");
    }

    private UserDetails findById(String id) {
        UUID uuid = UUID.fromString(id);
        return repository.findUserDetailsById(uuid).orElseThrow(
                () -> new TokenException("Access expired")
        );
    }

}