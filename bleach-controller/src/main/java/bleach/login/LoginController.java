
package bleach.login;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST }, path = { "/login1" }, produces = { "application/json;charset=UTF-8", "text/plain;charset=UTF-8" })
	public Map<String, String> login() {
		Map<String, String> m = new HashMap<String, String>();
		m.put("RESULT", "success");
		return m;
	}

}
