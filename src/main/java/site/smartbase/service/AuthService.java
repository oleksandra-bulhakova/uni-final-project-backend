package site.smartbase.service;

public interface AuthService{
    String generateToken(String email);

    Long getCurrentUserId(String token);
}
