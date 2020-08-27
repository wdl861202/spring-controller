package drr.security;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 自定义密码解密
 *
 * @author wangdl
 *
 */
public class DRRPasswordEncoder implements PasswordEncoder {

	@Override
	public String encode(CharSequence rawPassword) {
		// TODO
		return String.valueOf(rawPassword);
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		// TODO
		if (StringUtils.equalsIgnoreCase(String.valueOf(rawPassword),
				String.valueOf(encodedPassword))) {
			return true;
		}
		return false;
	}

}
