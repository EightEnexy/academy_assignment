package sk.ness.academy.service;

import org.springframework.stereotype.Service;
import sk.ness.academy.dao.ArticleDAO;
import sk.ness.academy.dao.CommentDAO;
import sk.ness.academy.domain.Comment;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CommentImpl implements CommentService {

    @Resource
    private CommentDAO commentDAO;

    @Override
    public List<Comment> getAllComments(final Integer articleId) {
        return commentDAO.getAllComments(articleId);
    }
    @Override
    public void addComment(final Comment comment) {
        commentDAO.persist(comment);
    }

    @Override
    public void deleteByID(final Integer commentId) {
        commentDAO.deleteByID(commentId);
    }

    @Override
    public Comment findByID(final Integer commentId) {
        return commentDAO.findByID(commentId);
    }

    @Override
    public void deleteAllCommentsByID(final Integer articleId) {
        commentDAO.deleteAllCommentsByID(articleId);
    }
}
