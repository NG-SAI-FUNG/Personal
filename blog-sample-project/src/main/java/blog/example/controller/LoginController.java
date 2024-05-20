package blog.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import blog.example.model.entity.Users;
import blog.example.service.UserService;
import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private HttpSession session;
	
	@GetMapping("/admin/login")
	public String getLoginPage(Model model) {
		model.addAttribute("error", false);
		return "login";
	}
	
	@PostMapping("/admin/login/process")
	public String  registerProcess(
			@RequestParam String userEmail,
			@RequestParam String password,
			Model model) {
		// logicCheckメソッドを呼び出してその結果adminという変数に格納
		Users users = userService.loginCheck(userEmail, password);
		// もし、admin == nullログイン画面にとどまります
		// そうでない場合は、sessionログイン情報に保存
		// 商品一覧画面にリダイレクトする/product/list
		if(users == null) {
			model.addAttribute("error", true);
			return "login";
		} else {
			session.setAttribute("loginSession", users);
			return "redirect:/blog/list";
		}
	}
}
