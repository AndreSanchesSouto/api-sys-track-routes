package br.com.api_str_innovation.service;

import br.com.api_str_innovation.entities.user.UserEntity;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    public String generateToken(UserEntity employee) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                            .withIssuer("auth-api")
                            .withSubject(employee.getLogin())
                            .withExpiresAt(generateTimer())
                            .sign(algorithm);
        } catch (JWTCreationException jwtError) {
            throw new JWTCreationException("Error generating token", jwtError);
        }
    }

    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("auth-api")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTCreationException jwtError) {
            return "";
        }
        catch (TokenExpiredException expired) {
            System.out.println("etapora");
            return "etaporra";
        }
    }

    private Instant generateTimer() {
        return LocalDateTime.now().plusDays(1).toInstant(ZoneOffset.of("-03:00"));
    }

}
