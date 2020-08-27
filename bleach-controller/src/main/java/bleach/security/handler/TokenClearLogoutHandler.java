
package drr.security.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import drr.security.userdetails.DRRUserDetailsService;
import drr.security.userdetails.DRRUserdetailsServiceDelegate;

public class TokenClearLogoutHandler extends DRRUserdetailsServiceDelegate implements LogoutHandler {


	public TokenClearLogoutHandler(DRRUserDetailsService drrUserDetailsService) {
		super(drrUserDetailsService);
	}

	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) {
		clearToken(authentication);
	}

	protected void clearToken(Authentication authentication) {
		if (authentication == null) {
			return;
		}

		UserDetails user = (UserDetails) authentication.getPrincipal();
		if (user != null && user.getUsername() != null) {
			getDRRUserDetailsService().removeUser(user.getUsername());
		}
	}

}
