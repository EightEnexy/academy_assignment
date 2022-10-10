package sk.ness.academy.service;

import java.util.List;

import sk.ness.academy.domain.Article;

public interface ArticleService {

	  /** Returns {@link Article} with provided ID */
	  Article findByID(Integer articleId);

	  /** Returns all available {@link Article}s */
	  List<Article> findAll();

	  /** Creates new {@link Article} */
	  void createArticle(Article article);

	  /** Returns all found {@link Article}s */
	  List<Article> searchByText(final String searchText);

	  /** Creates new {@link Article}s by ingesting all articles from json */
	  void ingestArticles(String jsonArticles);

	  /** Deletes new {@link Article} with provided ID */
	  void deleteByID(Integer articleId);

	}
