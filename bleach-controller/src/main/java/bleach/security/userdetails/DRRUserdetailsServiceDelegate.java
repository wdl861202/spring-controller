package drr.security.userdetails;

import org.springframework.beans.factory.annotation.Autowired;

public abstract class DRRUserdetailsServiceDelegate {
	
	@Autowired
	private DRRUserDetailsService drrUserDetailsService;
	
	public DRRUserdetailsServiceDelegate(DRRUserDetailsService drrUserDetailsService) {
		this.drrUserDetailsService= drrUserDetailsService;
	}

	public DRRUserDetailsService getDRRUserDetailsService() {
		return drrUserDetailsService;
	}

	public void setDRRUserDetailsService(DRRUserDetailsService drrUserDetailsService) {
		this.drrUserDetailsService = drrUserDetailsService;
	}
	
}
