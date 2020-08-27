
package drr.security.userdetails.cache;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.core.userdetails.UserDetails;

import drr.api.sm.read.feign.QueryAuthFeign;
import drr.security.userdetails.DRRGrantedAuthority;
import drr.security.userdetails.DRRUser;

@CacheConfig(cacheManager = "redisCacheManager", cacheNames = { "userCache" })
public class DRRUserCache implements UserCache {

	@Autowired
	private QueryAuthFeign queryAuth;

	@Override
	@Cacheable(key = "#username")
	public UserDetails getUserFromCache(String username) {
		return new DRRUser(username, "", getAuthorities(username));
	}

	@Override
	@CachePut(key = "#user.username")
	public void putUserInCache(UserDetails user) {
	}

	@Override
	@CacheEvict(key = "#username")
	public void removeUserFromCache(String username) {
	}

	private Collection<? extends GrantedAuthority> getAuthorities(String username) {
		Set<DRRGrantedAuthority> authorities = queryAuth.listAuth(username).parallelStream().map(auth -> convertToDRRGrantedAuthority(auth)).collect(Collectors.toSet());
		return authorities;
	}

	private DRRGrantedAuthority convertToDRRGrantedAuthority(String auth) {
		return new DRRGrantedAuthority(auth);
	}
}
