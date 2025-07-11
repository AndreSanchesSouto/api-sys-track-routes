package br.com.api_str_innovation.infrastructure.security;

import br.com.api_str_innovation.infrastructure.configuration.WebConfiguration;
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
    private SecurityFilter securityFilter;

    @Autowired
    private WebConfiguration webConfiguration;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(webConfiguration.corsConfigurationSource()))
                .sessionManagement( session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorized -> authorized
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/register").permitAll()

                        .requestMatchers(HttpMethod.GET, "/user").hasAnyRole("ADMIN", "SHIPPING")
                        .requestMatchers(HttpMethod.GET, "/user/drivers").hasAnyRole("ADMIN", "SHIPPING")
                        .requestMatchers(HttpMethod.GET, "/user/page").hasAnyRole("ADMIN", "SHIPPING")
                        .requestMatchers(HttpMethod.GET, "/user/search/name").hasAnyRole("ADMIN", "SHIPPING")
                        .requestMatchers(HttpMethod.GET,    "/user/count").hasAnyRole("ADMIN", "SHIPPING")
                        .requestMatchers(HttpMethod.POST, "/user").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/user/period-of-creation").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/user/*").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/user/*").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/user").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/user").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.POST, "/delivery/report/period").hasAnyRole("ADMIN", "SHIPPING")
                        .requestMatchers(HttpMethod.POST, "/delivery").hasAnyRole("ADMIN", "SHIPPING")
                        .requestMatchers(HttpMethod.PUT, "/delivery").hasAnyRole("ADMIN", "SHIPPING")
                        .requestMatchers(HttpMethod.PATCH, "/delivery", "/delivery/inactive/*").hasAnyRole("ADMIN", "SHIPPING")
                        .requestMatchers(HttpMethod.GET, "/logs/delivery/*").hasAnyRole("ADMIN", "SHIPPING")
                        .requestMatchers(HttpMethod.GET, "/logs/delivery/details/*").hasAnyRole("ADMIN", "SHIPPING")

                        .requestMatchers(HttpMethod.GET, "/clients").hasAnyRole("ADMIN", "SHIPPING")
                        .requestMatchers(HttpMethod.GET, "/clients/available").hasAnyRole("ADMIN", "SHIPPING")
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
                        .requestMatchers(HttpMethod.GET, "/products/search").hasAnyRole("ADMIN","SHIPPING")
                        .requestMatchers(HttpMethod.GET, "/products/available").hasAnyRole("ADMIN", "SHIPPING")
                        .requestMatchers(HttpMethod.GET, "/products/*").hasAnyRole("ADMIN", "SHIPPING")
                        .requestMatchers(HttpMethod.POST, "/products").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/products/*").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/products/*").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/products/count").hasAnyRole("ADMIN", "SHIPPING")
                        .requestMatchers(HttpMethod.DELETE, "/products/*").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.GET, "/vehicle").hasAnyRole("ADMIN","SHIPPING")
                        .requestMatchers(HttpMethod.GET, "/vehicle/page").hasAnyRole("ADMIN", "SHIPPING")
                        .requestMatchers(HttpMethod.GET, "/vehicle/available").hasAnyRole("ADMIN", "SHIPPING")
                        .requestMatchers(HttpMethod.POST, "/vehicle").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/vehicle/*").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/vehicle/*").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/vehicle/*").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.GET, "/logs/checklist/vehicle/*").hasAnyRole("ADMIN", "SHIPPING")
                        .requestMatchers(HttpMethod.GET, "/checklist").hasAnyRole("ADMIN", "SHIPPING")
                        .requestMatchers(HttpMethod.GET, "/checklist/*").hasAnyRole("ADMIN", "SHIPPING")
                        .requestMatchers(HttpMethod.POST, "/checklist/count-km/*").hasAnyRole("ADMIN", "SHIPPING")
                        .requestMatchers(HttpMethod.POST, "/checklist/km-segments/*").hasAnyRole("ADMIN", "SHIPPING")
                        .requestMatchers(HttpMethod.PUT, "/checklist/*").hasAnyRole("ADMIN", "SHIPPING")
                        .requestMatchers(HttpMethod.DELETE, "/checklist/*").hasAnyRole("ADMIN", "DRIVER")

                        .anyRequest().authenticated()
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
