package sk.ness.academy.dao;

import sk.ness.academy.domain.Comment;

import java.util.List;

public interface CommentDAO {

    /** Returns all available {@link Comment}s */
    List<Comment> getAllComments(final Integer articleId);

    /** Creates new {@link Comment} */
    void persist(final Comment comment);

    /** Deletes {@link Comment} by Id*/
    void deleteByID(final Integer commentId);

    /** Find {@link Comment} by Id*/
    Comment findByID(final Integer commentId);

    void deleteAllCommentsByID(final Integer articleId);
}
