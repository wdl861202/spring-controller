
package drr.security.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import drr.security.JwtUtil;
import drr.security.userdetails.DRRUserDetailsService;
import drr.security.userdetails.DRRUserdetailsServiceDelegate;

public class OALoginSuccessHandler extends DRRUserdetailsServiceDelegate
		implements AuthenticationSuccessHandler {

	private static Logger log = LoggerFactory.getLogger(JwtRefreshSuccessHandler.class);

	public OALoginSuccessHandler(DRRUserDetailsService drrUserDetailsService) {
		super(drrUserDetailsService);
	}

	/**设置token*/
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		try {
			response.setHeader("Authorization", JwtUtil.getToken(authentication));
		} catch (Exception e) {
			log.info("生成Token失败！", e);
			throw new ServletException(e);
		}
	}

}
