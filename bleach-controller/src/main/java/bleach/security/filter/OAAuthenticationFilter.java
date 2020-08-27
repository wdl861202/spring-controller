
package drr.security.filter;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.Assert;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;

import drr.constant.controller.PathConst;
import drr.security.token.OAAuthenticationToken;
import drr.utils.GsonUtils;

public class OAAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

	public OAAuthenticationFilter() {
		super(new AntPathRequestMatcher(PathConst.LOGIN_URL, "POST"));
	}

	@Override
	public void afterPropertiesSet() {
		Assert.notNull(getAuthenticationManager(), "authenticationManager must be specified");
		Assert.notNull(getSuccessHandler(), "AuthenticationSuccessHandler must be specified");
		Assert.notNull(getFailureHandler(), "AuthenticationFailureHandler must be specified");
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {

		String body = StreamUtils.copyToString(request.getInputStream(), Charset.forName("UTF-8"));

		String username = "";
		String password = "";

		if (StringUtils.hasText(body)) {
			Map<String, Object> jsonMap = GsonUtils.parseMap(body);
			username = String.valueOf(jsonMap.get("username"));
			password = String.valueOf(jsonMap.get("password"));
		}

		username = username.trim();

		OAAuthenticationToken authRequest = new OAAuthenticationToken(username, password);

		return getAuthenticationManager().authenticate(authRequest);
	}

}
