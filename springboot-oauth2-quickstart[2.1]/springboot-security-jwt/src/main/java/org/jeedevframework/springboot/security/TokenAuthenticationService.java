package org.jeedevframework.springboot.security;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
 
public class TokenAuthenticationService {
     
    static final long EXPIRATIONTIME = 24*60*60*1000; //1天
     
    static final String SECRET = "SECRET";
     
    static final String TOKEN_PREFIX = "Bearer";
     
    static final String HEADER_STRING = "Authorization";
 
    public static String addAuthentication(HttpServletResponse res, String username) {
        String JWT = Jwts.builder().setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
        //res.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + JWT);
        //return TOKEN_PREFIX + " " + JWT;
        return JWT;
    }
 
    // JWT验证方法
    public static Authentication getAuthentication(HttpServletRequest request,UserDetailsService userDetailsService) {
        String token = request.getHeader(HEADER_STRING);
        if (token != null) {
            // parse the token.
            Claims claims = Jwts.parser()
                    .setSigningKey(SecurityConfigurer.SECRET)
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                    .getBody();
            
        	String user = claims.getSubject();
            if (user == null) {
                return null;
            }
            UserDetails userDetails = userDetailsService.loadUserByUsername(user);
            
            /*
            List<String> scopes = claims.get("scopes", List.class);
            List<GrantedAuthority> authorities = scopes.stream()
                    .map(authority -> new SimpleGrantedAuthority(authority))
                    .collect(Collectors.toList());
            */
            // 得到 权限（角色）
            //List<GrantedAuthority> authorities =  AuthorityUtils.commaSeparatedStringToAuthorityList((String) claims.get("authorities"));

            return new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
        }
        return null;
    }
     
}