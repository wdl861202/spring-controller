
package drr.security.configurer;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutFilter;

import drr.security.filter.UsernamePasswordAuthenticationFilter;
import drr.security.handler.UnauthorizedLoginFailureHandler;

/**
 * 第一次登陆生成token使用
 *
 * @author wangdl
 *
 * @param <T>
 * @param <B>
 */
public class UsernamePasswordLoginConfigurer<T extends UsernamePasswordLoginConfigurer<T, B>, B extends HttpSecurityBuilder<B>>
		extends AbstractHttpConfigurer<T, B> {

	private UsernamePasswordAuthenticationFilter authFilter;

	public UsernamePasswordLoginConfigurer() {
		this.authFilter = new UsernamePasswordAuthenticationFilter();
	}

	/**
	 * 设置在LogoutFilter之后进行过滤
	 */
	@Override
	public void configure(B http) throws Exception {
		authFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
		authFilter.setAuthenticationFailureHandler(new UnauthorizedLoginFailureHandler());

		UsernamePasswordAuthenticationFilter filter = postProcess(authFilter);
		http.addFilterAfter(filter, LogoutFilter.class);
	}

	/**
	 * 设置认证成功处理
	 *
	 * @param authSuccessHandler
	 * @return
	 */
	public UsernamePasswordLoginConfigurer<T, B> loginSuccessHandler(
			AuthenticationSuccessHandler authSuccessHandler) {
		authFilter.setAuthenticationSuccessHandler(authSuccessHandler);
		return this;
	}

}
