package main.ecom.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {
    String SECRET_KEY = "12345678900987654321123456789009876543211234567890098765432112345678900987654321";
    
    public String generateToken(String userId) {
        Map<String, Object> claim = new HashMap<>();
        return createToken(claim, userId);
    }

    @SuppressWarnings("deprecation")
    private String createToken(Map<String, Object> claim, String userId) {
        return Jwts.builder()
        .setClaims(claim)
        .subject(userId)
        .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
        .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
        .compact();
    }

}
