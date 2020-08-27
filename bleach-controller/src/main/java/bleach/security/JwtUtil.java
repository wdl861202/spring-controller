
package drr.security;

import java.io.UnsupportedEncodingException;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.www.NonceExpiredException;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator.Builder;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.impl.PublicClaims;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Verification;

import drr.security.token.JwtAuthenticationToken;

public class JwtUtil {

	private JwtUtil() {
		throw new IllegalStateException("Utility class");
	}

	@SuppressWarnings("unchecked")
	public static String getToken(Authentication authentication) throws IllegalArgumentException, UnsupportedEncodingException {
		Algorithm algorithm = Algorithm.HMAC256(getSalt(authentication.getName()));

		Builder jwtBuilder = JWT.create().withSubject(String.valueOf(authentication.getName())).withExpiresAt(getExpires()).withIssuedAt(new Date()).withIssuer(ISSUER);
		if (authentication.getDetails() instanceof Map) {
			Map<Object, Object> map = (Map<Object, Object>) authentication.getDetails();
			map.entrySet().stream().forEach(entry -> jwtBuilder.withClaim(String.valueOf(entry.getKey()), String.valueOf(entry.getValue())));
		}

		return jwtBuilder.sign(algorithm);
	}

	public static void verityToken(Authentication authentication) {
		DecodedJWT jwt = ((JwtAuthenticationToken) authentication).getToken();

		if (jwt.getExpiresAt().before(Calendar.getInstance().getTime())) {
			throw new NonceExpiredException("Token已过期！");
		}

		String username = jwt.getSubject();
		try {
			Algorithm algorithm = Algorithm.HMAC256(getSalt(username));
			Verification v = JWT.require(algorithm).withSubject(username).acceptExpiresAt(jwt.getExpiresAt().getTime()).acceptIssuedAt(jwt.getIssuedAt().getTime()).withIssuer(jwt.getIssuer());
			jwt.getClaims().forEach((key, value) -> {
				if (!IGNORE_CLAIM.contains(key)) {
					v.withClaim(key, value.asString());
				}
			});

			v.build().verify(jwt.getToken());
		} catch (Exception e) {
			throw new BadCredentialsException("Token验证失败！", e);
		}
	}

	private static Date getExpires() {
		return Date.from(Instant.now().plusMillis(EXPIRES_TIME)); // 设置1小时后过期
	}

	private static String getSalt(String name) {
		StringBuilder sb = new StringBuilder();
		sb.append(name).append(SALT);
		return sb.toString();
	}

	private static final Set<String> IGNORE_CLAIM = new HashSet<>();
	static {
		IGNORE_CLAIM.add(PublicClaims.ISSUER);
		IGNORE_CLAIM.add(PublicClaims.EXPIRES_AT);
		IGNORE_CLAIM.add(PublicClaims.NOT_BEFORE);
		IGNORE_CLAIM.add(PublicClaims.ISSUED_AT);
	}

	// TODO配置文件
	private static final long EXPIRES_TIME = 3600000;

	private static final String SALT = "DRR_SALT";

	private static final String ISSUER = "DRR";
}
