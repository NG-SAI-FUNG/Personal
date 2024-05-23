package blog.example.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import blog.example.model.entity.Blog;
import blog.example.model.entity.Profile;

@Repository
public interface ProfileDao extends JpaRepository<Profile, Long> {
	// 保存処理と更新処理 insertとupdate
	Profile save(Profile profile);
	
	// SELECT * FROM profile
	// ブログ一覧を表示させるときに使用
	List<Profile>findAll();
	
	// SELECT * FROM profile WHERE profile_id = ?
	// 編集画面を表示する際に使用
	Profile findByProfileId(Long profileId);
	
	// SELECT * FROM profile WHERE profile_image = ?
	// 編集画面を表示する際に使用
	Profile findByProfileImage(String profileImage);
	
	Profile findByUserId(Long userId);;
}
