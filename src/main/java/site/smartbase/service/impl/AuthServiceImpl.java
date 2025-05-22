package site.smartbase.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import site.smartbase.service.AuthService;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final JwtEncoder jwtEncoder;

    @Override
    public String generateToken(String email) {
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .subject(email)
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plus(1, ChronoUnit.HOURS))
                .build();

        JwsHeader jwsHeader = JwsHeader.with(() -> "HS256").build();

        return jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claims)).getTokenValue();
    }
}