package sk.ness.academy.service;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import sk.ness.academy.dao.ArticleDAO;
import sk.ness.academy.domain.Article;

@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {

  @Resource
  private ArticleDAO articleDAO;

  @Override
  public Article findByID(final Integer articleId) {
	  return this.articleDAO.findByID(articleId);
  }

  @Override
  public List<Article> findAll() {
	  return this.articleDAO.findAll();
  }

  @Override
  public void createArticle(final Article article) {
	  this.articleDAO.persist(article);
  }

  @Override
  public void ingestArticles(final String jsonArticles) {
    ObjectMapper mapper = new ObjectMapper();
    try {
      List<Article> articles = mapper.readValue(jsonArticles, new TypeReference<List<Article>>(){});
      articles.forEach(e -> articleDAO.persist(e));
    }
    catch (JsonProcessingException e) {
      e.printStackTrace();
    }
  }
  @Override
  public void deleteByID(Integer articleId) {
    this.articleDAO.deleteByID(articleId);
  }

  @Override
  public List<Article> searchByText(final String searchText) {
    return this.articleDAO.searchByText(searchText);
  }

}
