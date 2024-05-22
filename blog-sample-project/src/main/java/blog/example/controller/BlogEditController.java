package blog.example.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import blog.example.model.entity.Blog;
import blog.example.model.entity.Users;
import blog.example.service.BlogService;
import jakarta.servlet.http.HttpSession;

@Controller
public class BlogEditController {
	@Autowired
	private BlogService blogService;
	
	@Autowired
	private HttpSession session;
	
	// blog_edit.htmlの表示
	@GetMapping("/blog/edit/{blogId}")
	public String getBlogEditPage(@PathVariable Long blogId, Model model) {
		// session("loginSession")からログインしているデータをusersに入れる
		Users users =  (Users) session.getAttribute("loginSession");
		// if , user = null -> login.html
		if (users == null) {
			return "redirect:/admin/login";
		} else {
			// edit.htmlに表示するデータはblogのobject変数に代入
			Blog blog = blogService.blogEditCheck(blogId);
			// if , blog = null -> blog.html
			// if not , BlogIdに当たってDBデータをhtmlに渡す, blog_edit.htmlを表示する
			if(blog == null) {
				return "redirect:/blog/list";
				} else {
					model.addAttribute("userName", users.getUserName());
					model.addAttribute("blog", blog);
					return "blog_edit";
				}
			}
		}
		// 編集処理
	@PostMapping("/blog/edit/process")
	public String blogEditProcess( 
			@RequestParam MultipartFile blogImage,
			@RequestParam String blogTitle,
			@RequestParam String categoryName,
			@RequestParam String message,
			@RequestParam String blogDate,
			@RequestParam Long blogId) {
		// セッションからログインしている人情報をusersという変数に格納
		Users users =  (Users) session.getAttribute("loginSession");
		// if users = null -> login.html
		// if not ①　画像のファイル名を取得 ②　画像のアップロード
		// if 同じファイルの名前がなかったら保存 -> blog.html
		// if not ブログ編集画面にstay
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
			
			if(blogService.BlogUpdate(blogId, fileName, blogTitle, categoryName, message, blogDate, users.getUserId())) {
				return "redirect:/blog/list";
			} else {
				return "redirect:/blog/edit" + blogId;
			}
		}
	}
}
