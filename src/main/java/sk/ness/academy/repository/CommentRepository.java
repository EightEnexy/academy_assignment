package sk.ness.academy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sk.ness.academy.domain.Article;
import sk.ness.academy.domain.Comment;
import java.util.List;
@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

    List<Comment> findByArticleId(Integer articleId);


}
