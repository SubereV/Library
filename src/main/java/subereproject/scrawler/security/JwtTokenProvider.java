package subereproject.scrawler.security;


import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import subereproject.scrawler.config.AppProperties;

@Service
public class JwtTokenProvider {
	private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class); 
	
	private AppProperties appProperties; 
		
	public JwtTokenProvider(AppProperties appProperties) {
		super();
		this.appProperties = appProperties;
	}

	public String generateToken(Authentication authentication) {
		System.out.println(authentication.getName());
		System.out.println(authentication.getDetails());
		UserPrincipal userPrincipal = (UserPrincipal)authentication.getPrincipal();
		
		Date now = new Date(); 
		Date expiryDate = new Date(now.getTime() + appProperties.getAuth().getTokenExpirationMsec());
		
		return Jwts.builder()
				.setSubject(Long.toBinaryString(userPrincipal.getId()))
				.setIssuedAt(new Date())
				.setExpiration(expiryDate)				
				.signWith(SignatureAlgorithm.HS512, appProperties.getAuth().getTokenSecret())
				.compact();
	}
	
	public Long getUserIdFromToken(String token) {
		Claims claims = Jwts.parser()
						.setSigningKey(appProperties.getAuth().getTokenSecret())
						.parseClaimsJws(token)
						.getBody();
		return Long.parseLong(claims.getSubject()); 
	}
	
	public boolean validateToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(appProperties.getAuth().getTokenSecret()).parseClaimsJws(authToken);
			return true;
		} catch (SignatureException e) {
			logger.error("Invalid JWT signature");
		} catch (MalformedJwtException e) {
			logger.error("Invalid JWT token");
		} catch (ExpiredJwtException e) {
			logger.error("Expired JWT token");
		} catch (UnsupportedJwtException e) {
			logger.error("Unsupported JWT token");
		} catch (IllegalArgumentException e) {
			logger.error("JWT claims string is empty");
		}
		return false; 
	}
	
	
}
