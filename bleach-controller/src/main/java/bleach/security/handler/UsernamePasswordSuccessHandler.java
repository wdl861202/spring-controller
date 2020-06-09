
package bleach.security.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import bleach.security.JwtUserService;

public class UsernamePasswordSuccessHandler implements AuthenticationSuccessHandler {

	private JwtUserService jwtUserService;

	public UsernamePasswordSuccessHandler(JwtUserService jwtUserService) {
		this.jwtUserService = jwtUserService;
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		String token = jwtUserService.saveUserLoginInfo((UserDetails) authentication.getPrincipal());
		response.setHeader("Authorization", token);
	}

}
