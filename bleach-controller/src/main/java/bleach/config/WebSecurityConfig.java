
package drr.config;

import java.util.Arrays;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.header.Header;
import org.springframework.security.web.header.writers.StaticHeadersWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import drr.constant.controller.PathConst;
import drr.security.configurer.JwtLoginConfigurer;
import drr.security.configurer.OALoginConfigurer;
import drr.security.filter.OptionsRequestFilter;
import drr.security.handler.DRRHttpStatusReturningLogoutSuccessHandler;
import drr.security.handler.JwtRefreshSuccessHandler;
import drr.security.handler.OALoginSuccessHandler;
import drr.security.handler.TokenClearLogoutHandler;
import drr.security.provider.JwtAuthenticationProvider;
import drr.security.provider.OAAuthenticationProvider;
import drr.security.userdetails.DRRUserDetailsService;
import drr.security.userdetails.cache.DRRUserCache;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private DRRUserDetailsService drrUserDetailsService;

	@Resource(name = "jwtAuthenticationProvider")
	private AuthenticationProvider jwtAuthenticationProvider;

	@Resource(name = "oaAuthenticationProvider")
	private AuthenticationProvider oaAuthenticationProvider;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers(PathConst.LOGIN_URL).permitAll().anyRequest().authenticated();
		http.cors().disable();
		http.csrf().disable();
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.formLogin().disable();
		http.headers().addHeaderWriter(new StaticHeadersWriter(Arrays.asList(new Header("Access-control-Allow-Origin", "*"), new Header("Access-Control-Expose-Headers", "Authorization"))));
		http.addFilterAfter(new OptionsRequestFilter(), CorsFilter.class);
		http.apply(new OALoginConfigurer<>()).loginSuccessHandler(oaLoginSuccessHandler());
		http.apply(new JwtLoginConfigurer<>()).tokenValidSuccessHandler(jwtRefreshSuccessHandler()).permissiveRequestUrls("/logout").and().logout().addLogoutHandler(tokenClearLogoutHandler())
				.logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());
		http.logout().logoutRequestMatcher(new AntPathRequestMatcher(PathConst.LOGOUT_URL)).logoutSuccessHandler(new DRRHttpStatusReturningLogoutSuccessHandler());
	}

	/** 认证配置 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(jwtAuthenticationProvider).authenticationProvider(oaAuthenticationProvider);
	}

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	/** 认证处理 */
	@Bean("jwtAuthenticationProvider")
	protected AuthenticationProvider jwtAuthenticationProvider() {
		return new JwtAuthenticationProvider(drrUserDetailsService);
	}

	@Bean("oaAuthenticationProvider")
	protected AuthenticationProvider oaAuthenticationProvider() {
		return new OAAuthenticationProvider(drrUserDetailsService);
	}

	/** 用户信息 */
	@Bean("drrUserDetailsService")
	protected DRRUserDetailsService drrUserDetailsService() {
		return new DRRUserDetailsService();
	}

	@Bean("drrUserCache")
	protected DRRUserCache drrUserCache() {
		return new DRRUserCache();
	}

	/** 过滤后的处理 */
	@Bean
	protected OALoginSuccessHandler oaLoginSuccessHandler() {
		return new OALoginSuccessHandler(drrUserDetailsService);
	}

	@Bean
	protected JwtRefreshSuccessHandler jwtRefreshSuccessHandler() {
		return new JwtRefreshSuccessHandler(drrUserDetailsService);
	}

	@Bean
	protected TokenClearLogoutHandler tokenClearLogoutHandler() {
		return new TokenClearLogoutHandler(drrUserDetailsService);
	}

	@Bean
	protected CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList("*"));
		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "HEAD", "OPTION"));
		configuration.setAllowedHeaders(Arrays.asList("*"));
		configuration.addExposedHeader("Authorization");
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
}
