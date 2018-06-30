package org.jeedevframework.springboot.oauth2.web;

import java.io.UnsupportedEncodingException;

import org.jeedevframework.springboot.oauth2.config.JwtTokenConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@RestController
@RequestMapping("/api")
public class ApiController {
 
    @Autowired
    private TokenStore tokenStore;
 
    @RequestMapping("/sayHello")
    public String sayHello(
    		@RequestHeader(value="Authorization",defaultValue="") String auth,
    		@RequestParam(value = "access_token", required = false) String access_token
    		) {
    	String req_access_token = null;
    	if(!StringUtils.isEmpty(auth)){
    		req_access_token = auth.split(" ")[1];
    	}else{
    		req_access_token = access_token;
    	}
    	if(!StringUtils.isEmpty(req_access_token)){
    		 User userDetails = (User) tokenStore.readAuthentication(req_access_token).getPrincipal();
    	     System.out.println(userDetails.getUsername());
    	     
    	     
    	     
    	     try {
				Claims claims = Jwts.parser()
						.setSigningKey(JwtTokenConfig.SECRET.getBytes("UTF-8"))
						.parseClaimsJws(req_access_token)
						.getBody();
				//{aud=[oauth2-resource], user_name=demoUser1, scope=[all], exp=1530340775, authorities=[USER], jti=8b72e921-8a16-4eef-a34c-7e855583b27f, client_id=demoApp}
				String username = (String) claims.get("user_name");
				//String client_id = (String) claims.get("client_id");
				System.out.println("fromJwt.parser:"+username);
			} catch (ExpiredJwtException e) {
				e.printStackTrace();
			} catch (UnsupportedJwtException e) {
				e.printStackTrace();
			} catch (MalformedJwtException e) {
				e.printStackTrace();
			} catch (SignatureException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
    	}
        return "Hello";
    }
}