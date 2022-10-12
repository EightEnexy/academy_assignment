package sk.ness.academy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sk.ness.academy.domain.Article;
import sk.ness.academy.dto.Author;
import sk.ness.academy.dto.AuthorStats;
import sk.ness.academy.dto.AuthorStatsInt;

import java.util.List;
@Repository
public interface AuthorRepository extends JpaRepository<Article, Integer> {

    @Query(value = "SELECT author AS authorName, COUNT(id) AS articleCount FROM articles  GROUP BY author", nativeQuery = true)
    List<AuthorStats> getCountByAuthor();
}
