package blog.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import blog.example.model.entity.Blog;
import blog.example.model.entity.Profile;
import blog.example.model.entity.Users;
import blog.example.service.BlogService;
import blog.example.service.ProfileService;
import jakarta.servlet.http.HttpSession;

@Controller
public class BlogListController {
	@Autowired
	private HttpSession session;
	
	@Autowired
	private BlogService blogService;
	
	@Autowired
	private ProfileService profileService;
	
	@GetMapping("/blog/list")
	public String getBloglist(Model model) {
		// セッションからログインしている情報を取得
				Users users =  (Users) session.getAttribute("loginSession");
				// もし、users == null ログイン画面にリダイレクトする
				// そうでない場合
				// ログインしている人の名前の情報を画面に渡して商品一覧のhtmlを表示。
				if ( users == null) {
					return "redirect:/admin/login";
				}else {
					// Blogのデータを取得する
					List<Blog> blogList = blogService.selectAllBlog(users.getUserId());
					List<Profile> profile = profileService.showProfile(users.getUserId());
					model.addAttribute("userName", users.getUserName());
					model.addAttribute("blogList", blogList);
					model.addAttribute("profile", profile);
					return "blog";
				}
	}
	
}
