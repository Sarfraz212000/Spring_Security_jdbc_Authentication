package in.sbms.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserRsetController {
	
	@GetMapping("/admin")
	public String Admin()
	{
		return "welcome Admin";
	}
	
	@GetMapping("/user")
	public String user()
	{
		return "welcome User";
	}
	
	@GetMapping("/greet")
	public String greet()
	{
		return "Good morning user and Admin";
	}


}
