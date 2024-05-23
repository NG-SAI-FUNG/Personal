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
		public List<Profile> showProfile(Long userId){
			if(userId == null) {
				return null;
			}else {
				return profileDao.findAll();
			}
		}
		
		
		
		// プロファイルの登録処理
		// profile_imageあるかどうかチェック
		// if, profileImage = null -> profile save
		// if not, そのprofileIdの内容を編集可能になります。
		public boolean createProfile(String profileImage,String profileMessage,Long userId) {
			if(profileDao.findByProfileImage(profileImage) == null) {
				profileDao.save(new Profile(profileImage, profileMessage, userId));
				return true;
			}else {
				return false;
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
		
		// profile編集画面表示するときのチェック
		// if , profileId = null -> null;
		// if not , findByProfileIDでcontrollerに返す
		public Profile profileEditCheck(Long profileId) {
			if (profileId == null ) {
				return null;
			}else {
				return profileDao.findByProfileId(profileId);
			}
		}
		
		public Profile getProfileByImage(String profileImage) {
	        return profileDao.findByProfileImage(profileImage);
	    }
		
		public Profile getProfileByUserId(Long userId) {
			return profileDao.findByUserId(userId);
	    }
}
