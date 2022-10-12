package sk.ness.academy.service;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import sk.ness.academy.domain.Article;
import sk.ness.academy.domain.Comment;

import java.util.List;

public interface CommentService {

    /** Returns all available {@link Comment}s */
    List<Comment> getAllComments(final Integer articleId);

    /** Creates new {@link Comment} */
    void addComment(final Comment comment);
    /** Deletes {@link Comment} */
    void deleteByID(final Integer commentId);

    /** Returns {@link Comment} */
    Comment findByID(final Integer commentId);

    /** Deletes all articles{@link Comment}s */
    void deleteAllCommentsByID(final Integer articleId);

}
