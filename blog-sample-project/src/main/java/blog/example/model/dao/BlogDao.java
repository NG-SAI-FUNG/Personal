package blog.example.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import blog.example.model.entity.Blog;
import blog.example.model.entity.Users;

@Repository
public interface BlogDao extends JpaRepository<Blog, Long> {
	// 保存処理と更新処理 insertとupdate
	Blog save(Blog blog);
	
	// SELECT * FROM blog
	// ブログ一覧を表示させるときに使用
	List<Blog>findAll();
	
	// SELECT * FROM blog WHERE blog_title = ?
	// 商品の登録チェックに使用（同じ商品名が登録されないようにするチェックに使用）
	Blog findByBlogTitle(String blogTitle);
	
	// SELECT * FROM blog WHERE blog_id = ?
	// 編集画面を表示する際に使用
	Blog findByBlogId(Long blogId);
	
	// DELETE FROM blog WHERE blog_id = ?
	// 削除使用します
	void deleteByBlogId(Long blogId);
	
	// SELECT * FROM blog WHERE CategoryName = ?
	List<Blog> findByCategoryName(String categoryName);
}
