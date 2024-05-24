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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import blog.example.model.entity.Profile;
import blog.example.model.entity.Users;
import blog.example.service.ProfileService;
import jakarta.servlet.http.HttpSession;

@Controller
public class ProfileController {
	
	@Autowired
	private ProfileService profileService;
	
	@Autowired
	private HttpSession session;
	
	// profile.htmlの表示
	@GetMapping("/blog/profile")
	public String getProfilePage(Model model) {
		// セッションからログインしている人情報をusersという変数に格納
		Users users =  (Users) session.getAttribute("loginSession"); 
		// もし、users ==null ログイン画面にリダイレクトする
		// そうでない場合 ログインしている人の名前の情報を画面に渡して, blog_register.htmlを表示する。
		if ( users == null) {
			return "redirect:/admin/login";
		}else {
			model.addAttribute("userName", users.getUserName());
			return "profile";
		}
	}
	
	// プロファイルの登録
	@PostMapping("/blog/profile/process")
	public String profileCreateProcess(
			@RequestParam MultipartFile profileImage,
			@RequestParam String profileMessage,Model model) {
		// セッションからログインしている人情報をusersという変数に格納
		Users users =  (Users) session.getAttribute("loginSession");
		// if users = null -> login.html
		// if not ①　画像のファイル名を取得 ②　画像のアップロード
		if (users == null) {
			return "redirect:/admin/login";
		}else {
			// 画像をおくファイル名を取得
			String fileName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-").format(new Date())
					+ profileImage.getOriginalFilename();
			// ファイルの保存作業
			try {
				Files.copy(profileImage.getInputStream(), Path.of("src/main/resources/static/profile_img/" + fileName));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(profileService.createProfile(fileName, profileMessage, users.getUserId())) {
				return "redirect:/blog/list";
			}else {
				model.addAttribute("userName", users.getUserName());
				return "profile";
            }
		}
	}
	
	// profile_edit.htmlの表示
		@GetMapping("/blog/profile/edit/{profileId}")
		public String getProfileEditPage(@PathVariable Long profileId,Model model) {
			// セッションからログインしている人情報をusersという変数に格納
			Users users =  (Users) session.getAttribute("loginSession"); 
			// もし、users ==null ログイン画面にリダイレクトする
			// そうでない場合 ログインしている人の名前の情報を画面に渡して, blog_register.htmlを表示する。
			if ( users == null) {
				return "redirect:/admin/login";
			}else {
				Profile profile = profileService.profileEditCheck(profileId);
				if(profile == null) {
					return "redirect:/blog/list";
				} else {
				model.addAttribute("userName", users.getUserName());
				model.addAttribute("profile", profile);
				return "profile_edit";
				}
			}
		}
	
	// プロファイルの編集
	@PostMapping("/blog/profile/edit/process")
	public String profileEdit(
			@RequestParam MultipartFile profileImage,
			@RequestParam String profileMessage,Model model,
			@RequestParam Long profileId) {
		// セッションからログインしている人情報をusersという変数に格納
		Users users =  (Users) session.getAttribute("loginSession");
		// if users = null -> login.html
		// if not ①　画像のファイル名を取得 ②　画像のアップロード
		if (users == null) {
			return "redirect:/admin/login";
		}else {
			// 画像をおくファイル名を取得
			String fileName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-").format(new Date())
					+ profileImage.getOriginalFilename();
			// ファイルの保存作業
			try {
				Files.copy(profileImage.getInputStream(), Path.of("src/main/resources/static/profile_img/" + fileName));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            if(profileService.profileUpdate(profileId, fileName, profileMessage, users.getUserId())) {
            	return "redirect:/blog/list";
            } else {
            	return "redirect:/blog/list";
            }
		}
	}
	
	
}
