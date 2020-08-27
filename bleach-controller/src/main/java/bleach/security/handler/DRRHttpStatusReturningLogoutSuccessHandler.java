
package drr.security.handler;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

public class DRRHttpStatusReturningLogoutSuccessHandler extends HttpStatusReturningLogoutSuccessHandler {

	private final HttpStatus httpStatusToReturn;

	private final static String DATA = "{\"code\":20000, \"data\":\"success\"}";

	public DRRHttpStatusReturningLogoutSuccessHandler(HttpStatus httpStatusToReturn) {
		super(httpStatusToReturn);
		this.httpStatusToReturn = httpStatusToReturn;
	}

	public DRRHttpStatusReturningLogoutSuccessHandler() {
		super();
		httpStatusToReturn = HttpStatus.OK;
	}

	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
		response.setStatus(httpStatusToReturn.value());
		response.getWriter().append(DATA);
		response.getWriter().flush();
	}
}
