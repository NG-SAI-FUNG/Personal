package blog.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
	public String getBloglist(Model model,@RequestParam(name = "categoryName", required = false) String categoryName) {
		// セッションからログインしている情報を取得
				Users users =  (Users) session.getAttribute("loginSession");
				// もし、users == null ログイン画面にリダイレクトする
				// そうでない場合
				// ログインしている人の名前の情報を画面に渡して商品一覧のhtmlを表示。
				if ( users == null) {
					return "redirect:/admin/login";
				}else {
					// ユーザーの名前を渡す
					model.addAttribute("userName", users.getUserName());
					
					// Blogのデータを取得する
					List<Blog> blogList;
					// if categoryName != null and not empty -> findByCategoryname
					// if not , findAll
					if (categoryName != null && !categoryName.isEmpty()) {
						blogList = blogService.selectCategory(categoryName);
					} else {
						blogList = blogService.selectAllBlog(users.getUserId());
					}
					model.addAttribute("blogList", blogList);
					
					// プロファイルの表示
					Profile profile = profileService.showProfile(users.getUserId());
					if (profile == null) {
			            profile = new Profile();
			            profile.setProfileMessage("プロフィールが設定されていません。");
			        }
			        model.addAttribute("profile", profile);
					return "blog";
				}
	}
	
}
