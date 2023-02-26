package com.basware.ParkingLotManagementWeb.services.auth;

import com.basware.ParkingLotManagementCommon.models.users.User;
import com.basware.ParkingLotManagementCommon.models.users.UserType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.mapping.SimpleAttributes2GrantedAuthoritiesMapper;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class JwtService {

    @Value("${JWT_SECRET_KEY}")
    private String SECRET_KEY;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractPayload(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(User user, Date tokenExpirationDate) {
        return Jwts
                .builder()
                .setClaims(getUserAuthorities(user.getAuthorities()))
                .addClaims(getUserType(user.getUserType()))
                .addClaims(getIsValidatedAccount(user.getIsValidated()))
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(tokenExpirationDate)
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Map<String, Object> getIsValidatedAccount(boolean isValidatedAccount){
        Map<String, Object> map = new HashMap<>();
        map.put(User.IS_VALIDATED_FIELD, isValidatedAccount);
        return map;
    }

    private Map<String, Object> getUserType(UserType userType){
        Map<String, Object> map = new HashMap<>();
        map.put(User.USER_TYPE_FIELD, userType);
        return map;
    }

    private Map<String, Object> getUserAuthorities(Collection<? extends GrantedAuthority> grantedAuthorities){
        final String authorities = grantedAuthorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        Map<String, Object> userRoles = new HashMap<>();
        userRoles.put(User.USER_ROLES_FIELD, authorities);
        return userRoles;
    }

    public boolean isTokenValid(String token) {
        final String username = extractUsername(token);
        return (username != null) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public Collection<String> extractRoles(String token){
       String rolesString = extractPayload(token).get(User.USER_ROLES_FIELD, String.class);
       String[] rolesSplit = rolesString.split(",");
       return Arrays.stream(rolesSplit).collect(Collectors.toUnmodifiableList());
    }

    public List<GrantedAuthority> getAuthorities(String token){
        Collection<String> roles = extractRoles(token);
        return new SimpleAttributes2GrantedAuthoritiesMapper().getGrantedAuthorities(roles);
    }

    private Claims extractPayload(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
