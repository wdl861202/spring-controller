package main.java.service.login;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("main.java.login.ILogin")
public interface ILogin {

	@RequestMapping("/login")
	public String login(@RequestParam(value = "name") String name,@RequestParam(value = "password") String password);

}
