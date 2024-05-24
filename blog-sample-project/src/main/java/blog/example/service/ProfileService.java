package blog.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import blog.example.model.dao.ProfileDao;
import blog.example.model.entity.Blog;
import blog.example.model.entity.Profile;

@Service
public class ProfileService {
		@Autowired
		private ProfileDao profileDao;
		
		// プロファイルの一覧のチェック
		// もしprofileId == null 戻り値としてnull
		// findAll内容をコントローラークラスに渡す
		public Profile showProfile(Long userId){
			if(userId == null) {
				return null;
			}else {
				return profileDao.findByUserId(userId);
			}
		}
		
		
		
		// プロファイルの登録処理
		// profile_imageあるかどうかチェック
		// if, profileImage = null -> profile save
		// if not, そのprofileIdの内容を編集可能になります。
		public boolean createProfile(String profileImage,String profileMessage,Long userId) {
			if(profileDao.findByUserId(userId) == null) {
				profileDao.save(new Profile(profileImage, profileMessage, userId));
				return true;
			}else {
				return false;
			}
		}
		
		public Profile profileEditCheck(Long profileId) {
			if(profileId == null) {
				return null;
			}else {
				return profileDao.findByProfileId(profileId);
			}
		}
		
		// 編集処理のチェック
		// if , ProfileId = null -> false
		// if not , 更新処理する
		public boolean profileUpdate(Long profileId, String profileImage,String profileMessage,Long userId) {
			if(profileId == null) {
				return false;
			}else {
				Profile profile = profileDao.findByProfileId(profileId);
				profile.setProfileImage(profileImage);
				profile.setProfileMessage(profileMessage);
				profile.setUserId(userId);
				profileDao.save(profile);
				return true;
			}
		}
		
}
