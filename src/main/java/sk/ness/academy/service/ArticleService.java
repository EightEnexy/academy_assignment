package sk.ness.academy.service;
import sk.ness.academy.domain.Article;

public interface ArticleService {
    /** Creates new {@link Article}s by ingesting all articles from json */
    void ingestArticles(String jsonArticles);
}
