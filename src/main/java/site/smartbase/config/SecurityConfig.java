package site.smartbase.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import site.smartbase.enums.UserRole;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/register",
                                "/api/auth/login",
                                "/api/auth/confirm",
                                "/api/auth/continue-registration",
                                "/api/auth/confirm-user-registration",
                                "/api/auth/finish-registration",
                                "/api/auth/finish",
                                "/api/auth/forgot-password",
                                "/api/auth/change-password",
                                "/api/auth/set-password",
                                "/error").permitAll()
                        .requestMatchers(HttpMethod.POST,
                                "/api/auth/invite").hasAnyRole(String.valueOf(UserRole.OWNER))
                        .requestMatchers(HttpMethod.GET,
                                "/api/users/{userId}",
                                "/api/clients",
                                "/api/users",
                                "/api/technologies",
                                "/api/vacancies",
                                "/api/vacancies/user",
                                "/api/vacancies/{vacancyId}",
                                "/api/clients/{clientId}",
                                "/api/candidates",
                                "/api/candidates/{candidateId}").hasAnyRole(String.valueOf(UserRole.OWNER), String.valueOf(UserRole.RECRUITER), String.valueOf(UserRole.HIRING_MANAGER))
                        .requestMatchers(HttpMethod.POST,
                                "/api/files/upload",
                                "/api/clients",
                                "/api/vacancies",
                                "/api/address",
                                "/api/candidates",
                                "/api/attachments/{candidateId}").hasAnyRole(String.valueOf(UserRole.OWNER), String.valueOf(UserRole.RECRUITER), String.valueOf(UserRole.HIRING_MANAGER))
                        .requestMatchers(HttpMethod.PUT,
                                "/api/users/set-image-path/{userId}",
                                "/api/technologies/{candidateId}").hasAnyRole(String.valueOf(UserRole.OWNER), String.valueOf(UserRole.RECRUITER), String.valueOf(UserRole.HIRING_MANAGER))
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter()))
                )
                .build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        grantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");
        grantedAuthoritiesConverter.setAuthoritiesClaimName("role");

        JwtAuthenticationConverter jwtConverter = new JwtAuthenticationConverter();
        jwtConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);

        jwtConverter.setPrincipalClaimName("sub");

        return jwtConverter;
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:3000"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
