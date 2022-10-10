package sk.ness.academy.dao;

import java.util.List;

import sk.ness.academy.domain.Article;

public interface ArticleDAO {

	  /** Returns {@link Article} with provided ID */
	  Article findByID(Integer articleId);

	  /** Returns all available {@link Article}s */
	  List<Article> findAll();

	  void deleteByID(Integer articleId);

	  /** Persists {@link Article} into the DB */
	  void persist(Article article);

	  /** Returns all found {@link Article}s */
	  List<Article> searchByText(final String searchText);
	}
