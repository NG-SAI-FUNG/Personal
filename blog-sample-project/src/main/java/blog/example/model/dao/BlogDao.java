package blog.example.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import blog.example.model.entity.Blog;

@Repository
public interface BlogDao extends JpaRepository<Blog, Long> {

}
