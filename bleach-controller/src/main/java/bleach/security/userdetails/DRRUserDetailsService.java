
package bleach.security.userdetails;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * 从数据库获取，可由其他service进行封装，也可直接使用
 *
 * @author wangdl
 *
 */
public class DRRUserDetailsService implements UserDetailsService {

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO 1、通过数据库获取用户信息和权限信息

		return new User(username, "", getAuthorities(username));
	}

	private Collection<? extends GrantedAuthority> getAuthorities(String username) {
		return new ArrayList<GrantedAuthority>();
	}
}
