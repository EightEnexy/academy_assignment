package sk.ness.academy.dao;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import sk.ness.academy.domain.Article;
import sk.ness.academy.domain.Comment;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class CommentHibernateDAO implements CommentDAO {

    @Resource(name = "sessionFactory")
    private SessionFactory sessionFactory;
    @Override
    public List<Comment> getAllComments(final Integer articleId) {
        return this.sessionFactory.getCurrentSession().createSQLQuery("select id, author, text, create_timestamp from comments where article_id = ?").
                setParameter(1, articleId)
                .list();
    }
    @Override
    public void persist(final Comment comment) {
        this.sessionFactory.getCurrentSession().saveOrUpdate(comment);
    }

    @Override
    public Comment findByID(final Integer commentId) {
        return (Comment) this.sessionFactory.getCurrentSession().get(Comment.class, commentId);
    }

    @Override
    public void deleteByID(final Integer commentId) {
        this.sessionFactory.getCurrentSession().delete(findByID(commentId));
    }

}
