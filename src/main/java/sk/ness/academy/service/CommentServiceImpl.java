package sk.ness.academy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import sk.ness.academy.domain.Comment;
import sk.ness.academy.exception.BlogException;
import sk.ness.academy.repository.ArticleRepository;
import sk.ness.academy.repository.CommentRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {
    @Autowired
    CommentRepository commentRepository;

    public List<Comment> findByArticleId(final Integer articleId) {
        return this.commentRepository.findByArticleId(articleId);
    }

    public void save(final Comment comment) {
        this.commentRepository.save(comment);
    }

    public void deleteById(final Integer commentId) {
        this.commentRepository.deleteById(commentId);
    }

    public Comment findById(final Integer commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new BlogException("Comment " + commentId + " not found", HttpStatus.NOT_FOUND));
    }
}
