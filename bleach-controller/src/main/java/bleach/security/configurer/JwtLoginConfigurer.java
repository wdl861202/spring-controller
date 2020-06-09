
package bleach.security.configurer;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutFilter;

import bleach.security.filter.JwtAuthenticationFilter;
import bleach.security.handler.UnauthorizedLoginFailureHandler;

/**
 * 前端上传token，后台验证使用
 *
 * @author wangdl
 *
 * @param <T>
 * @param <B>
 */
public class JwtLoginConfigurer<T extends JwtLoginConfigurer<T, B>, B extends HttpSecurityBuilder<B>>
		extends AbstractHttpConfigurer<T, B> {

	private JwtAuthenticationFilter authFilter;

	public JwtLoginConfigurer() {
		this.authFilter = new JwtAuthenticationFilter();
	}

	/**
	 * 设置在LogoutFilter之前进行过滤
	 */
	@Override
	public void configure(B http) throws Exception {
		authFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
		authFilter.setAuthenticationFailureHandler(new UnauthorizedLoginFailureHandler());

		JwtAuthenticationFilter filter = postProcess(authFilter);
		http.addFilterBefore(filter, LogoutFilter.class);
	}

	public JwtLoginConfigurer<T, B> permissiveRequestUrls(String... urls) {
		authFilter.setPermissiveUrl(urls);
		return this;
	}

	public JwtLoginConfigurer<T, B> tokenValidSuccessHandler(
			AuthenticationSuccessHandler successHandler) {
		authFilter.setAuthenticationSuccessHandler(successHandler);
		return this;
	}

}
