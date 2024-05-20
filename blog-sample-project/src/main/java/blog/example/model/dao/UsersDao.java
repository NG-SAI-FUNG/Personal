package blog.example.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import blog.example.model.entity.Users;

@Repository
public interface UsersDao extends JpaRepository<Users, Long> {
		// 保存処理と更新処理 insertとupdate
		Users save(Users users);
		
		// SELECT * FROM users WHERE user_email = ?
		// ユーザーの登録処理をするときに、同じメールアドレスがあったらば登録させないようにする
		// 1行だけしかレコードは取得できない
		Users findByUserEmail(String userEmail);
		
		// SELECT * FROM users WHERE user_email = ? AND password=?
		// ログイン処理に使用。入力したメールアドレスとパスワードが一致している時だけデータを取得する
		Users findByUserEmailAndPassword(String userEmail, String password);
}
