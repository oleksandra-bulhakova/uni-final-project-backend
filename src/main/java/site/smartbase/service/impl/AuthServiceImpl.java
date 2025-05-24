package site.smartbase.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import site.smartbase.entity.User;
import site.smartbase.enums.ContactType;
import site.smartbase.enums.OwnableType;
import site.smartbase.exception.NotFoundException;
import site.smartbase.repository.ContactRepo;
import site.smartbase.repository.UserRepo;
import site.smartbase.service.AuthService;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final JwtEncoder jwtEncoder;
    private final UserRepo userRepo;
    private final ContactRepo contactRepo;

    @Override
    public String generateToken(String email) {
        User user = userRepo.findById(contactRepo.findOwnerIdByEmail(email, OwnableType.USER, ContactType.MAIN_EMAIL)
                        .orElseThrow(() -> new NotFoundException("Email wasn't found")))
                .orElseThrow(() -> new NotFoundException("User not found"));

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .subject(email)
                .claim("role", user.getRole())
                .claim("status", user.getActive().toString())
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plus(1, ChronoUnit.HOURS))
                .build();

        JwsHeader jwsHeader = JwsHeader.with(() -> "HS256").build();

        return jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claims)).getTokenValue();
    }
}