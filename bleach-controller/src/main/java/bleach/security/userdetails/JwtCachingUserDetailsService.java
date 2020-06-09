
package bleach.security.userdetails;

import org.springframework.security.authentication.CachingUserDetailsService;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * 优先缓存获取，其次数据库获取
 *
 * @author wangdl
 *
 */
public class JwtCachingUserDetailsService extends CachingUserDetailsService {

	public JwtCachingUserDetailsService(UserDetailsService delegate) {
		super(delegate);
	}

	public void removeUserByUserName(String username) {
		getUserCache().removeUserFromCache(username);
	}
}
