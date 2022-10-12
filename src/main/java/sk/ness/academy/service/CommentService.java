package sk.ness.academy.service;

import sk.ness.academy.domain.Comment;

import java.util.List;

public interface CommentService {

    List<Comment> findByArticleId(final Integer articleId);

    void save(final Comment comment);

    void deleteById(final Integer commentId);

    Comment findById(final Integer commentId);

    void deleteByArticleId(final Integer articleId);


}
