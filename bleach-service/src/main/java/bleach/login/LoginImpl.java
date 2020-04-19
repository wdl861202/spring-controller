package bleach.login;

import javax.annotation.PostConstruct;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import main.java.login.ILogin;

@RestController
public class LoginImpl  {

	@PostConstruct
	public void init() {
		String a="";
	}
	
	@RequestMapping(method= {RequestMethod.GET,RequestMethod.POST},path= {"/login"},produces = { "application/json;charset=UTF-8" ,"text/plain;charset=UTF-8"})
	public String login(String name, String password) {
		return "SUCCESS";
	}

	@RequestMapping(method= {RequestMethod.GET,RequestMethod.POST},path= {"/login1"},produces = { "application/json;charset=UTF-8" ,"text/plain;charset=UTF-8"})
	public String login() {
		return "SUCCESS";
	}
	
}
