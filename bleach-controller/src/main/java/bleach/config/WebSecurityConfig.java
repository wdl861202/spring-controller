
package bleach.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.header.Header;
import org.springframework.security.web.header.writers.StaticHeadersWriter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import bleach.security.DRRPasswordEncoder;
import bleach.security.JwtUserService;
import bleach.security.configurer.JwtLoginConfigurer;
import bleach.security.configurer.UsernamePasswordLoginConfigurer;
import bleach.security.filter.OptionsRequestFilter;
import bleach.security.handler.JwtRefreshSuccessHandler;
import bleach.security.handler.TokenClearLogoutHandler;
import bleach.security.handler.UsernamePasswordSuccessHandler;
import bleach.security.provider.JwtAuthenticationProvider;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/login").permitAll().anyRequest().authenticated();
		http.cors().disable();
		http.csrf().disable();
		http.sessionManagement().disable();
		http.formLogin().disable();
		http.headers()
				.addHeaderWriter(new StaticHeadersWriter(
						Arrays.asList(new Header("Access-control-Allow-Origin", "*"),
								new Header("Access-Control-Expose-Headers", "Authorization"))));
		http.addFilterAfter(new OptionsRequestFilter(), CorsFilter.class);
		http.apply(new UsernamePasswordLoginConfigurer<>())
				.loginSuccessHandler(jsonLoginSuccessHandler());
		http.apply(new JwtLoginConfigurer<>()).tokenValidSuccessHandler(jwtRefreshSuccessHandler())
				.permissiveRequestUrls("/logout").and().logout()
				.addLogoutHandler(tokenClearLogoutHandler())
				.logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(daoAuthenticationProvider())
				.authenticationProvider(jwtAuthenticationProvider());
	}

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean("jwtAuthenticationProvider")
	protected AuthenticationProvider jwtAuthenticationProvider() {
		return new JwtAuthenticationProvider(jwtUserService());
	}

	@Bean("daoAuthenticationProvider")
	protected AuthenticationProvider daoAuthenticationProvider() throws Exception {
		DaoAuthenticationProvider daoProvider = new DaoAuthenticationProvider();
		daoProvider.setUserDetailsService(userDetailsService());
		daoProvider.setPasswordEncoder(new DRRPasswordEncoder());
		return daoProvider;
	}

	@Override
	protected UserDetailsService userDetailsService() {
		return new JwtUserService();
	}

	@Bean("jwtUserService")
	protected JwtUserService jwtUserService() {
		return new JwtUserService();
	}

	@Bean
	protected UsernamePasswordSuccessHandler jsonLoginSuccessHandler() {
		return new UsernamePasswordSuccessHandler(jwtUserService());
	}

	protected JwtRefreshSuccessHandler jwtRefreshSuccessHandler() {
		return new JwtRefreshSuccessHandler(jwtUserService());
	}

	@Bean
	protected TokenClearLogoutHandler tokenClearLogoutHandler() {
		return new TokenClearLogoutHandler(jwtUserService());
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
