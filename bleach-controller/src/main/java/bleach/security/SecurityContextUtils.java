
package drr.security;

import java.util.Map;
import java.util.Optional;

import org.springframework.security.core.context.SecurityContextHolder;

import drr.constant.sm.AuthenticationDetailsConst;
import drr.constant.sm.StaffConst;

public final class SecurityContextUtils {

	/** 获取人员Id */
	@SuppressWarnings("unchecked")
	public static Integer getStaffId() {
		Object details = SecurityContextHolder.getContext().getAuthentication().getDetails();
		if (details instanceof Map) {
			Map<Object, Object> m = (Map<Object, Object>) details;
			return Optional.ofNullable(Integer.valueOf(String.valueOf(m.get(AuthenticationDetailsConst.STAFF_ID)))).orElse(StaffConst.DEFAULT_ID);
		}

		return StaffConst.DEFAULT_ID;
	}
}
