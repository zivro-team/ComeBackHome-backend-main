package project.comebackhomebe.domain.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.comebackhomebe.domain.post.entity.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
}
