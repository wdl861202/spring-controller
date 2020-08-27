package drr.security.oa;

import java.io.Serializable;

public class OASessionResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	private String id;
	private String isAllowZp;
	private String loginName;
	private String loginStatus;
	private String sessionId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIsAllowZp() {
		return isAllowZp;
	}

	public void setIsAllowZp(String isAllowZp) {
		this.isAllowZp = isAllowZp;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getLoginStatus() {
		return loginStatus;
	}

	public void setLoginStatus(String loginStatus) {
		this.loginStatus = loginStatus;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

}
