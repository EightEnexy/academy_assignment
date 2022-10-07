package sk.ness.academy.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import sk.ness.academy.domain.Article;

@Repository
public class ArticleHibernateDAO implements ArticleDAO {

  @Resource(name = "sessionFactory")
  private SessionFactory sessionFactory;

  @Override
  public Article findByID(final Integer articleId) {
    return (Article) this.sessionFactory.getCurrentSession().get(Article.class, articleId);
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<Article> findAll() {
    return this.sessionFactory.getCurrentSession().createSQLQuery("select * from articles").addEntity(Article.class).list();
  }

  @Override
  public void persist(final Article article) {
    this.sessionFactory.getCurrentSession().saveOrUpdate(article);
  }

  public void deleteByID(Integer articleId) {
    this.sessionFactory.getCurrentSession().delete(findByID(articleId));
  }

  public List<Article> searchByText(final String searchText) {
    return this.sessionFactory.getCurrentSession().createSQLQuery("select * from articles where title LIKE ? OR text LIKE ? OR author LIKE ?")
            .setParameter(1, "%" + searchText + "%").setParameter(2, "%" + searchText + "%").setParameter(3, "%" + searchText + "%").addEntity(Article.class).list();
  }

}
