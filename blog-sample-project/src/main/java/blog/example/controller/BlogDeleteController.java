package blog.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import blog.example.model.entity.Users;
import blog.example.service.BlogService;
import jakarta.servlet.http.HttpSession;

@Controller
public class BlogDeleteController {
		@Autowired
		private BlogService blogservice;
		
		@Autowired
		private HttpSession session;
		
		@PostMapping("/blog/delete")
		public String blogDelete(Long blogId) {
			// session("loginSession")からログインしているデータをusersに入れる
			Users users =  (Users) session.getAttribute("loginSession");
			// if users = null -> login.html
			if (users == null) {
				return "redirect:/admin/login";
				// if not , deleteBlogでdbIDに当たって削除,
			} else {
				// if  -> blog.html & if not -> remain on blog_edit.html (blogId) 
				if(blogservice.deleteBlog(blogId)) { 
				return "redirect:/blog/list";
			} else {
				return "redirect:/blog/edit" + blogId;
			}
		}
	}
}