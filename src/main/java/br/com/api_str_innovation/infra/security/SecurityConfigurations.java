package br.com.api_str_innovation.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {

    @Autowired
    SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .cors( cors -> cors.configurationSource(corsConfigurationSource()))
                .sessionManagement( session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorized -> authorized
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/register").permitAll()

                        .requestMatchers(HttpMethod.GET, "/user").hasAnyRole("ADMIN", "SHIPPING")
                        .requestMatchers(HttpMethod.GET, "/user/drivers").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/user/page").hasAnyRole("ADMIN", "SHIPPING")
                        .requestMatchers(HttpMethod.GET, "/user/search/name").hasAnyRole("ADMIN", "SHIPPING")
                        .requestMatchers(HttpMethod.GET, "/user/count").hasAnyRole("ADMIN", "SHIPPING")
                        .requestMatchers(HttpMethod.GET, "/user/*").hasAnyRole("ADMIN", "SHIPPING")
                        .requestMatchers(HttpMethod.POST, "/user").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/user/*").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/user/period-of-creation").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/user/*").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/user/*").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/user").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/user").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.POST, "/delivery").hasAnyRole("ADMIN", "SHIPPING")
                        .requestMatchers(HttpMethod.PUT, "/delivery").hasAnyRole("ADMIN", "SHIPPING")
                        .requestMatchers(HttpMethod.PATCH, "/delivery", "/delivery/inactive/*").hasAnyRole("ADMIN", "SHIPPING")

                        .requestMatchers(HttpMethod.GET, "/clients").hasAnyRole("ADMIN", "SHIPPING")
                        .requestMatchers(HttpMethod.GET, "/clients/available").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/clients/count").hasAnyRole("ADMIN", "SHIPPING")
                        .requestMatchers(HttpMethod.GET, "/clients/page").hasAnyRole("ADMIN", "SHIPPING")
                        .requestMatchers(HttpMethod.GET, "/clients/search").hasAnyRole("ADMIN", "SHIPPING")
                        .requestMatchers(HttpMethod.GET, "/clients/*").hasAnyRole("ADMIN", "SHIPPING")
                        .requestMatchers(HttpMethod.POST, "/clients").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/clients/*").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/clients/*").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/clients/*").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.GET, "/address").hasAnyRole("ADMIN", "SHIPPING")
                        .requestMatchers(HttpMethod.GET, "/address/client/*").hasAnyRole("ADMIN", "SHIPPING")
                        .requestMatchers(HttpMethod.GET, "/address/*").hasAnyRole("ADMIN", "SHIPPING")
                        .requestMatchers(HttpMethod.POST, "/address/*").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/address/*").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/address/*").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.GET, "/products").hasAnyRole("ADMIN","SHIPPING")
                        .requestMatchers(HttpMethod.GET, "/products/search/name").hasAnyRole("ADMIN","SHIPPING")
                        .requestMatchers(HttpMethod.GET, "/products/available").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/products/*").hasAnyRole("ADMIN", "SHIPPING")
                        .requestMatchers(HttpMethod.POST, "/products").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/products/*").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/products/*").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/products/count").hasAnyRole("ADMIN", "SHIPPING")
                        .requestMatchers(HttpMethod.DELETE, "/products/*").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.GET, "/vehicle").hasAnyRole("ADMIN","SHIPPING")
                        .requestMatchers(HttpMethod.GET, "/vehicle/page").hasAnyRole("ADMIN", "SHIPPING")
                        .requestMatchers(HttpMethod.GET, "/vehicle/available").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/vehicle").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/vehicle/*").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/vehicle/*").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/vehicle/*").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.DELETE, "/vehicle/*").hasAnyRole("ADMIN", "SHIPPING")
                        .anyRequest().authenticated()
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    UrlBasedCorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:5173"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type", "Accept", "general-manager-id", "user-id"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
