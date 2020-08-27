package drr.security.configurer;



import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutFilter;

import drr.security.filter.JwtAuthenticationFilter;
import drr.security.filter.OAAuthenticationFilter;
import drr.security.handler.UnauthorizedLoginFailureHandler;

/**
 * OA登录配置
 *
 * @author wangdl
 *
 * @param <T>
 * @param <B>
 */
public class OALoginConfigurer<T extends OALoginConfigurer<T, B>, B extends HttpSecurityBuilder<B>>
		extends AbstractHttpConfigurer<T, B> {

	private OAAuthenticationFilter authFilter;

	public OALoginConfigurer() {
		this.authFilter = new OAAuthenticationFilter();
	}

	/**
	 * 设置在LogoutFilter之前进行过滤
	 */
	@Override
	public void configure(B http) throws Exception {
		authFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
		authFilter.setAuthenticationFailureHandler(new UnauthorizedLoginFailureHandler());

		OAAuthenticationFilter filter = postProcess(authFilter);
		http.addFilterBefore(filter, LogoutFilter.class);
	}

	/**
	 * 设置认证成功处理
	 *
	 * @param authSuccessHandler
	 * @return
	 */
	public OALoginConfigurer<T, B> loginSuccessHandler(
			AuthenticationSuccessHandler authSuccessHandler) {
		authFilter.setAuthenticationSuccessHandler(authSuccessHandler);
		return this;
	}

}
