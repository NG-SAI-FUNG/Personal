package blog.example.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import blog.example.service.UserService;

@Controller
public class RegisterController {
	@Autowired
	private UserService userService;
	
	@GetMapping("/admin/register")
	public String getRegisterPage(Model model) {
		model.addAttribute("error", false);
		return "register";
	}
	@PostMapping("/admin/register/process")
	public String registerProcess(@RequestParam String userName,
								  @RequestParam String userEmail,
								  @RequestParam String password,
								  Model model) {
		//もし、createAdminがtrueだったら、admin_login.htmlに遷移
		//そうでない場合、admin_register.htmlにとどまります
		if(userService.createUser(userName, userEmail, password)) {
			return "login";
		} else {
			model.addAttribute("error", true);
			return "register";
		}
	}
	
}
