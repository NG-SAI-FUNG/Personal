package blog.example.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import blog.example.model.entity.Blog;
import blog.example.model.entity.Users;
import blog.example.service.BlogService;
import jakarta.servlet.http.HttpSession;

@Controller
public class BlogRegisterController {
	@Autowired
	private BlogService blogSerivce;
	
	@Autowired
	private HttpSession session;
	
	// ブログの追加画面の表示
	@GetMapping("/blog/register")
	public String getblogRegisterPage(Model model) {
		// セッションからログインしている人情報をusersという変数に格納
		Users users =  (Users) session.getAttribute("loginSession");
		// もし、users ==null ログイン画面にリダイレクトする
		// そうでない場合 ログインしている人の名前の情報を画面に渡して, blog_register.htmlを表示する。
		if ( users == null) {
			return "redirect:/admin/login";
		}else {
			model.addAttribute("error", false);
			model.addAttribute("userName", users.getUserName());
			return "blog_register";
		}
	}
	
	//　ブログ登録処理
	@PostMapping("/blog/register/process")
	public String blogRegisterProcess( 
			@RequestParam MultipartFile blogImage,
			@RequestParam String blogTitle,
			@RequestParam String categoryName,
			@RequestParam String message,
			@RequestParam String blogDate,
			Model model) {
		// セッションからログインしている人情報をusersという変数に格納
		Users users =  (Users) session.getAttribute("loginSession");
		// if users = null -> login.html
		// if not ①　画像のファイル名を取得 ②　画像のアップロード
		// if 同じファイルの名前がなかったら保存 -> blog.html
		// if not ブログ登録画面にstay
		if (users == null) {
			return "redirect:/admin/login";
		}else {
			// 画像をおくファイル名を取得
			String fileName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-").format(new Date()) 
					+ blogImage.getOriginalFilename();
			
			// ファイルの保存作業
			try {
				Files.copy(blogImage.getInputStream(), Path.of("src/main/resources/static/blog_img/" + fileName));
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			if(blogSerivce.createBlog(fileName, blogTitle, categoryName, message, blogDate, users.getUserId())) {
				return "redirect:/blog/list";
			}else {
				model.addAttribute("userName", users.getUserName());
				model.addAttribute("error", true);
				return "blog_register";
			}
		}

	}

}
