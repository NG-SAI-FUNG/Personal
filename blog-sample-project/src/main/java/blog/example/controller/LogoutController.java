package blog.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class LogoutController {
	@Autowired
	private HttpSession session;
	
	//logout !!!!
	@GetMapping("/admin/logout")
	public String logout() {
		//セッションの無効化
		session.invalidate();
		return "redirect:/admin/login";
	}
}
