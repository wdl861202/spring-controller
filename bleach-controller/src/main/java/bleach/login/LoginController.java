
package bleach.login;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

	@RequestMapping(method = { RequestMethod.POST }, path = { "/login" }, produces = {
			"application/json;charset=UTF-8", "text/plain;charset=UTF-8" })
	public Map<String, String> login() {
		Map<String, String> m = new HashMap<String, String>();
		m.put("RESULT", "login");
		return m;
	}

	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST }, path = {
			"/logout" }, produces = { "application/json;charset=UTF-8",
					"text/plain;charset=UTF-8" })
	public Map<String, String> logout() {
		Map<String, String> m = new HashMap<String, String>();
		m.put("RESULT", "logout");
		return m;
	}

	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST }, path = {
			"/service" }, produces = { "application/json;charset=UTF-8",
					"text/plain;charset=UTF-8" })
	public Map<String, String> service() {
		Map<String, String> m = new HashMap<String, String>();
		m.put("RESULT", "service");
		return m;
	}
}
