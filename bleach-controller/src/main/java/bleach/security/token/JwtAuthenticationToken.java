
package drr.security.token;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.auth0.jwt.interfaces.DecodedJWT;

import drr.constant.sm.AuthenticationDetailsConst;
import drr.security.provider.OAAuthenticationProvider;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {

	private static final long serialVersionUID = 3981518947978158945L;

	private UserDetails principal;
	private String credentials;
	private DecodedJWT token;

	public JwtAuthenticationToken(DecodedJWT token) {
		super(Collections.emptyList());
		this.token = token;
		initDetails(token);
	}

	public JwtAuthenticationToken(UserDetails principal, DecodedJWT token, Collection<? extends GrantedAuthority> authorities) {
		super(authorities);
		this.principal = principal;
		this.token = token;
		initDetails(token);
	}

	private void initDetails(DecodedJWT token) {
		Map<String, Object> map = new HashMap<>();
		for (String name : DRR_CLAIM_NAME) {
			if (null != token.getClaim(name)) {
				Object object = token.getClaim(name).as(Object.class);
				if (null != object) {
					map.put(name, object);
				}
			}
		}

		setDetails(map);
	}

	@Override
	public void setDetails(Object details) {
		super.setDetails(details);
		setAuthenticated(true);
	}

	@Override
	public Object getCredentials() {
		return credentials;
	}

	@Override
	public Object getPrincipal() {
		return principal;
	}

	public DecodedJWT getToken() {
		return token;
	}

	/** {@link OAAuthenticationProvider#getDetails} */
	private final static String[] DRR_CLAIM_NAME = { AuthenticationDetailsConst.STAFF_ID, AuthenticationDetailsConst.COMPANY };

}
