package com.medjamal.ouazani.springsecuritydemo.security;

import com.medjamal.ouazani.springsecuritydemo.SpringsecuritydemoApplication;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.medjamal.ouazani.springsecuritydemo.helpers.Constants.TOKEN_VALIDITY;

@Component
public class TokenUtil {

    private static Logger logger = LoggerFactory.getLogger(SpringsecuritydemoApplication.class);

    @Value("{$auth.secret}")
    private String TOKEN_SECRET;

    public String generateToken(UserDetails userDetails){

        Map<String, Object> claims = new HashMap<>();

        claims.put("sub", userDetails.getUsername());
        claims.put("createdAt", new Date());

        return Jwts
                .builder()
                .setClaims(claims)
                .setExpiration((generateTokenExpirationDate()))
                .signWith(SignatureAlgorithm.HS512, this.TOKEN_SECRET)
                .compact();
    }

    private Date generateTokenExpirationDate() {
        return new Date(System.currentTimeMillis() + TOKEN_VALIDITY * 1000);
    }

    public String getUserNameFromToken(String token){
        Claims claims;
        try {
            claims = getClaims(token);
            return claims.getSubject();
        } catch (Exception e) {
            logger.warn("Get UserName From Token Exception");
            claims = null;
            e.printStackTrace();
        }
        return null;
    }

    private Claims getClaims(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parser().setSigningKey(this.TOKEN_SECRET)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            logger.warn("ExpiredJwtException");
            e.printStackTrace();
        } catch (UnsupportedJwtException e) {
            logger.warn("UnsupportedJwtException");
            e.printStackTrace();
        } catch (MalformedJwtException e) {
            logger.warn("MalformedJwtException");
            e.printStackTrace();
        } catch (SignatureException e) {
            logger.warn("SignatureException");
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            logger.warn("IllegalArgumentException");
            e.printStackTrace();
        }

        return claims;
    }

    public boolean isTokenValid(String token, UserDetails userDetails){
        String username = this.getUserNameFromToken(token);
        if(username.equals(userDetails.getUsername()) && !isTokenExpired(token)) {
            return true;
        }
        return false;
    }

    private boolean isTokenExpired(String token){
        Date expirationDate = this.getClaims(token).getExpiration();
        return expirationDate.before(new Date());
    }
}
