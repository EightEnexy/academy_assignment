package sk.ness.academy.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sk.ness.academy.domain.Article;
import sk.ness.academy.dto.ArticleWithComments;
import sk.ness.academy.exception.BlogException;
import sk.ness.academy.repository.ArticleRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.jpa.domain.Specification.where;
import static sk.ness.academy.repository.ArticleRepository.*;

@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    ArticleRepository articleRepository;
    @Override
    public void ingestArticles(final String jsonArticles) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            List<Article> articles = mapper.readValue(jsonArticles, new TypeReference<List<Article>>(){});
            articles.forEach(e -> articleRepository.save(e));
        }
        catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Article> findAll() {
        return articleRepository.findAll();
    }

    @Override
    public Article findById(final Integer articleId) {
        return this.articleRepository.findById(articleId).
                orElseThrow(()-> new BlogException("Article " + articleId + " not found", HttpStatus.NOT_FOUND));
    }

    @Override
    public List<Article> findAll(final String searchText) {
        return this.articleRepository.findAll(
                where(titleContains(searchText)).or(textContains(searchText)).or(authorContains(searchText)));
    }

    @Override
    public void save(final Article article) {
        articleRepository.save(article);
    }

    @Override
    public void deleteArticle(final Integer articleId) {
        try {
            Optional<Article> article = articleRepository.findById(articleId);
            articleRepository.delete(article.get());
        }catch (Exception e) {
            throw new BlogException("Article " + articleId + "not found", HttpStatus.NOT_FOUND);
        }
    }
}
