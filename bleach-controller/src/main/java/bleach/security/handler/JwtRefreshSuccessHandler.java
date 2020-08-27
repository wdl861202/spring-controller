
package drr.security.handler;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.auth0.jwt.interfaces.DecodedJWT;

import drr.security.JwtUtil;
import drr.security.token.JwtAuthenticationToken;
import drr.security.userdetails.DRRUserDetailsService;
import drr.security.userdetails.DRRUserdetailsServiceDelegate;

public class JwtRefreshSuccessHandler extends DRRUserdetailsServiceDelegate implements AuthenticationSuccessHandler {

	private static Logger log = LoggerFactory.getLogger(JwtRefreshSuccessHandler.class);

	public JwtRefreshSuccessHandler(DRRUserDetailsService drrUserDetailsService) {
		super(drrUserDetailsService);
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		DecodedJWT jwt = ((JwtAuthenticationToken) authentication).getToken();

		if (shouldTokenRefresh(jwt.getIssuedAt())) {
			try {
				String newToken = JwtUtil.getToken(authentication);
				response.setHeader("Authorization", newToken);
			} catch (Exception e) {
				log.info("更新Token失败！", e);
				throw new ServletException(e);
			}
		}
	}

	protected boolean shouldTokenRefresh(Date issueAt) {
		LocalDateTime issueTime = LocalDateTime.ofInstant(issueAt.toInstant(), ZoneId.systemDefault());
		return LocalDateTime.now().minusSeconds(TOKEN_REFRESH_INTERVAL).isAfter(issueTime);
	}

	// TODO 配置文件
	private static final int TOKEN_REFRESH_INTERVAL = 600; // 刷新间隔10分钟

}
