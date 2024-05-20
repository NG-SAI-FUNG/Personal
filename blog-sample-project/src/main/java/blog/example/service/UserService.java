package blog.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import blog.example.model.dao.UsersDao;
import blog.example.model.entity.Users;

@Service
public class UserService {
	@Autowired
	private UsersDao usersDao;
	
	// 保存処理（登録処理)
	// もし、findByAdminEmail == nullだったら登録処理をします
	// saveメソッドを使用して登録をする
	// 保存ができたらTrue,なかったらfalse
	
	public boolean createUser(String userName,String userEmail,String password) {
		if(usersDao.findByUserEmail(userEmail) == null) {
			usersDao.save(new Users(userName, userEmail, password));
			return true;
		}else {
			return false;
		}
	}
	
	// ログイン処理
	// もし、emailとpasswordがfindByUserEmailAndPasswordを使用して存在しなかった場合 == nullの場合,
	// その場合は、存在しないnullであることをコントローラークラスに知らせる
	// そうでない場合はログインしている人の情報をコントローラークラスに渡す
	
	public Users loginCheck(String userEmail, String password) {
		Users users = usersDao.findByUserEmailAndPassword(userEmail, password);
		if (users == null) {
			return null;
		}else {
			return users;
		}
	}
}
