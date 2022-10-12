package sk.ness.academy.repository;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sk.ness.academy.domain.Article;
import sk.ness.academy.dto.AuthorStats;
import sk.ness.academy.dto.AuthorStatsInt;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository <Article, Integer>, JpaSpecificationExecutor<Article> {

    static Specification<Article> titleContains(String title) {
        return (article, cq, cb) -> cb.like(article.get("title"), "%" + title + "%");
    }

    static Specification<Article> textContains(String text) {
        return (article, cq, cb) -> cb.like(article.get("title"), "%" + text + "%");
    }

    static Specification<Article> authorContains(String author) {
        return (article, cq, cb) -> cb.like(article.get("title"), "%" + author + "%");
    }



}
