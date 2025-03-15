package codegym.c10.webservice.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;

@Service
public class JwtService {
    private static final String SECRET_KEY = "123456789987654321123456789987654321123456789";
    private static final long EXPIRE_TIME = 86400000L;

    public String generateTokenLogin(Map<String, Object> extraClaims,
                                     UserDetails userDetails){
        return Jwts
                .builder()
                .setClaims(extraClaims) // Thêm claims bổ sung (nếu có)
                .setSubject(userDetails.getUsername()) // Subject của JWT là username
                .setIssuedAt(new Date(System.currentTimeMillis())) // Thời điểm phát hành JWT
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_TIME)) // Thời điểm hết hạn JWT
                .signWith(getSignInKey(), SignatureAlgorithm.HS256) // Ký JWT bằng secret key và thuật toán HS256
                .compact(); // Build và compact JWT thành chuỗi
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parse(authToken);
            return true;
        } catch (MalformedJwtException e) {
            System.out.println("Invalid JWT token -> Message: " + e.getMessage());
        } catch (ExpiredJwtException e) {
            System.out.println("Expired JWT token -> Message: " + e.getMessage());
        } catch (UnsupportedJwtException e) {
            System.out.println("Unsupported JWT token -> Message: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("JWT claims string is empty -> Message: " + e.getMessage());
        }
        return false;
    }

    public String getUsernameFromJwtToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
