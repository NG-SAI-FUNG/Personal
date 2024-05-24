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
		
	Profile findByUserId(Long userId);
	
	Profile findByProfileId(Long profileId);
	
	List<Profile>findAll();
	
	
}
