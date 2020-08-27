package drr.security.oa;

import java.io.Serializable;

public class LoginRequest implements Serializable {
	private static final long serialVersionUID = 1L;
	private String loginName;
	private String password;
	private String method;

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	@Override
	public String toString() {
		return "LoginParam [loginName=" + loginName + ", password=" + password + ", method=" + method + "]";
	}

}
