package blog.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import blog.example.model.dao.BlogDao;
import blog.example.model.entity.Blog;
import jakarta.transaction.Transactional;

@Service
public class BlogService {
	@Autowired
	private BlogDao blogDao;
	
	// ブログ一覧のチェック
	// もしblogId == null 戻り値としてnull
	// findAll内容をコントローラークラスに渡す
	public List<Blog>selectAllBlog(Long userId){
		if(userId == null) {
			return null;
		}else {
			return blogDao.findAll();
		}
	}
	
	// ブログの登録処理チェック
	// もし、findbyBlogNameが　== nullだったら、
	// trueだったら 保存処理
	// そうでない場合 falseを返す
	public boolean createBlog(
			String blogImage, String blogTitle,
			String categoryName, String message,
			String blogDate,Long userId) {
		if(blogDao.findByBlogTitle(blogTitle) == null) {
			blogDao.save(new Blog(blogImage, blogTitle, categoryName, message, blogDate, userId));
			return true;
		}else {
			return false;
		}
	}
	
	// edit画面表示するときのチェック
	// if , blogId = null -> nullで返す
	// if not , findByBlogId(DBのID）controllerに渡す 
	public Blog blogEditCheck(Long blogId) {
		if(blogId == null) {
			return null;
		}else {
			return blogDao.findByBlogId(blogId);
		}
	}
	
	// 編集処理のチェック
	// もし、blogId == nullだったら、更新処理しない(falseを返す）
	// そうでない場合, 更新処理をする
	
	// コントローラークラスからもらった,blogIdを使って,編集する前の,でデータを取得
	// 変更するべきところだけ、セッターを使用してデータの更新をしてから, Trueを返す
	public boolean blogUpdate(Long blogId,
			String blogImage, String blogTitle,
			String categoryName, String message,
			String blogDate,Long userId) {
		if (blogId == null) {
			return false;
		}else {
			Blog blog = blogDao.findByBlogId(blogId);
			blog.setBlogImage(blogImage);
			blog.setBlogTitle(blogTitle);
			blog.setCategoryName(categoryName);
			blog.setMessage(message);
			blog.setBlogDate(blogDate);
			blog.setUserId(userId);
			blogDao.save(blog);
			return true;
		}
	}
	
	// ブログの削除
	// if, controller からもらったblogId == null だったら
	// 削除できないで false
	// not の場合は deleteByBlogidで削除します
	@Transactional
	public boolean deleteBlog(Long blogId) {
		if(blogId == null) {
			return false;
		}else {
			blogDao.deleteByBlogId(blogId);
			return true;
		}
	}

}
