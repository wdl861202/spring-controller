
package drr.security.userdetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import drr.security.userdetails.cache.DRRUserCache;

/**
 * 从数据库获取，可由其他service进行封装，也可直接使用
 *
 * @author wangdl
 *
 */
public class DRRUserDetailsService implements UserDetailsService {

	@Autowired
	private DRRUserCache drrUserCache;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return drrUserCache.getUserFromCache(username);
	}

	public void removeUser(String username) {
		drrUserCache.removeUserFromCache(username);
	}
}
