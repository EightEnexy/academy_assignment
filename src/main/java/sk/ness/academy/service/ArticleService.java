package sk.ness.academy.service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import sk.ness.academy.domain.Article;
import sk.ness.academy.dto.ArticleWithComments;

import java.util.List;

public interface ArticleService {
    /** Creates new {@link Article}s by ingesting all articles from json */
    void ingestArticles(String jsonArticles);

    List<Article> findAll();

    Article findById(final Integer articleId);

    List<Article> findAll(final String searchText);

    void save(final Article article);

    void deleteArticle(final Integer articleId);



}
