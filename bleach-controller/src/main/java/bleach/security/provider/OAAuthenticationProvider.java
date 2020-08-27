
package drr.security.provider;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

import drr.api.sm.dto.StaffDTO;
import drr.api.sm.read.feign.QueryStaffFeign;
import drr.api.sm.vo.StaffVO;
import drr.constant.sm.AuthenticationDetailsConst;
import drr.constant.sm.StaffConst;
import drr.security.oa.LoginRequest;
import drr.security.oa.OALoginResponse;
import drr.security.oa.OALoginService;
import drr.security.oa.OASessionResponse;
import drr.security.oa.OAStatusResponse;
import drr.security.token.OAAuthenticationToken;
import drr.security.userdetails.DRRUserDetailsService;
import drr.security.userdetails.DRRUserdetailsServiceDelegate;

public class OAAuthenticationProvider extends DRRUserdetailsServiceDelegate implements AuthenticationProvider {

	@Autowired
	private QueryStaffFeign queryStaffFeign;

	private static Logger log = LoggerFactory.getLogger(OAAuthenticationProvider.class);

	public OAAuthenticationProvider(DRRUserDetailsService drrUserDetailsService) {
		super(drrUserDetailsService);
	}

	@Autowired
	private OALoginService oaLoginService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = String.valueOf(authentication.getPrincipal());
		String password = String.valueOf(authentication.getCredentials());

		// 普通用户
		if (isCommonUser(username)) {
			LoginRequest loginRequest = new LoginRequest();
			loginRequest.setLoginName(username);
			loginRequest.setPassword(password);
			loginRequest.setMethod(METHOD_LOGIN);

			try {
				OALoginResponse loginResponse = oaLoginService.login(loginRequest);

				OAStatusResponse statusResponse = loginResponse.getOaStatusResponse();
				OASessionResponse sessionResponse = loginResponse.getOaSessionResponse();

				if (!RESP_CODE_SUCCESS.equals(statusResponse.getRespCode())) {
					log.debug("oa验证失败：" + loginRequest.getLoginName() + ", " + statusResponse.getRespMsg());
					throw new BadCredentialsException("OA验证失败!");
				}
				if (!LOGIN_STATUS_SUCCESS.equals(sessionResponse.getLoginStatus())) {
					log.debug("oa验证失败：" + loginRequest.getLoginName() + ", " + sessionResponse.getLoginStatus());
					throw new BadCredentialsException("OA验证失败!");
				}
			} catch (Exception e) {
				throw new BadCredentialsException("OA验证失败!", e);
			}
		}

		UserDetails user = getDRRUserDetailsService().loadUserByUsername(username);
		OAAuthenticationToken token = new OAAuthenticationToken(username, password, user.getAuthorities());
		token.setDetails(getDetails(username));

		return token;
	}

	private Map<Object, Object> getDetails(String username) {
		Map<Object, Object> details = new HashMap<>();
		Optional<StaffVO> staff = Optional.ofNullable(getStaff(username));
		if (staff.isPresent()) {
			details.put(AuthenticationDetailsConst.STAFF_ID, staff.get().getStaffId());
			details.put(AuthenticationDetailsConst.COMPANY, staff.get().getCompany());
		}
		return details;
	}

	private StaffVO getStaff(String username) {
		StaffDTO dto = new StaffDTO();
		dto.setUserCode(username);
		return queryStaffFeign.getStaff(dto);
	}

	private boolean isCommonUser(String username) {
		return !StaffConst.ADMIN.equalsIgnoreCase(username);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return OAAuthenticationToken.class.isAssignableFrom(authentication);
	}

	private static final String METHOD_LOGIN = "login";

	private static final String RESP_CODE_SUCCESS = "0000";

	private static final String LOGIN_STATUS_SUCCESS = "success";
}
