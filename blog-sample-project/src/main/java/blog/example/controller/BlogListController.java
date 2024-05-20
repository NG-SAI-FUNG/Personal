package blog.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import blog.example.model.entity.Users;
import jakarta.servlet.http.HttpSession;

@Controller
public class BlogListController {
	@Autowired
	private HttpSession session;
	
	@GetMapping("/blog/list")
	public String getBloglist(Model model) {
		// セッションからログインしている情報を取得
				Users users =  (Users) session.getAttribute("loginSession");
				// もし、admin==null ログイン画面にリダイレクトする
				// そうでない場合
				// ログインしている人の名前の情報を画面に渡して商品一覧のhtmlを表示。
				if ( users == null) {
					return "redirect:admin/login";
				}else {
					model.addAttribute("userName", users.getUserName());
					return "blog";
				}
	}
	
}
