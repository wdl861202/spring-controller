
package drr.security.provider;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

import com.auth0.jwt.interfaces.DecodedJWT;

import drr.security.JwtUtil;
import drr.security.token.JwtAuthenticationToken;
import drr.security.userdetails.DRRUserDetailsService;
import drr.security.userdetails.DRRUserdetailsServiceDelegate;

public class JwtAuthenticationProvider extends DRRUserdetailsServiceDelegate implements AuthenticationProvider {

	public JwtAuthenticationProvider(DRRUserDetailsService drrUserDetailsService) {
		super(drrUserDetailsService);
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		JwtUtil.verityToken(authentication);

		DecodedJWT jwt = ((JwtAuthenticationToken) authentication).getToken();
		String username = jwt.getSubject();
		UserDetails user = getDRRUserDetailsService().loadUserByUsername(username);

		JwtAuthenticationToken token = new JwtAuthenticationToken(user, jwt, user.getAuthorities());
		token.setDetails(authentication.getDetails());
		return token;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return JwtAuthenticationToken.class.isAssignableFrom(authentication);
	}

}
