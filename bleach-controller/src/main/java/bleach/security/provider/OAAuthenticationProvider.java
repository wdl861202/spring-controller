
package bleach.security.provider;

import java.util.Calendar;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.NonceExpiredException;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import bleach.security.JwtAuthenticationToken;
import bleach.security.JwtUserService;
import bleach.security.OAAuthenticationToken;

public class OAAuthenticationProvider implements AuthenticationProvider {

	private JwtUserService userService;

	public OAAuthenticationProvider(JwtUserService userService) {
		this.userService = userService;
	}

	@Override
	public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {
		DecodedJWT jwt = ((JwtAuthenticationToken) authentication).getToken();
		if (jwt.getExpiresAt().before(Calendar.getInstance().getTime())) {
			throw new NonceExpiredException("Token expires");
		}
		String username = jwt.getSubject();
		UserDetails user = userService.getUserLoginInfo(username);
		if (user == null || user.getPassword() == null) {
			throw new NonceExpiredException("Token expires");
		}
		String encryptSalt = user.getPassword();
		try {
			Algorithm algorithm = Algorithm.HMAC256(encryptSalt);
			JWTVerifier verifier = JWT.require(algorithm).withSubject(username).build();
			verifier.verify(jwt.getToken());
		} catch (Exception e) {
			throw new BadCredentialsException("JWT token verify fail", e);
		}
		JwtAuthenticationToken token = new JwtAuthenticationToken(user, jwt, user.getAuthorities());
		return token;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return OAAuthenticationToken.class.isAssignableFrom(authentication);
	}

}
