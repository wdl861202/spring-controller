package bleach.login;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

	@RequestMapping(method= {RequestMethod.GET,RequestMethod.POST},path= {"/login1"},produces = { "application/json;charset=UTF-8" ,"text/plain;charset=UTF-8"})
	public String login() {
		return "SUCCESS";
	}
	
}
