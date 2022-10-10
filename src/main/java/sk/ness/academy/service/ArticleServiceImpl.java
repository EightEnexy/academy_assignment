package sk.ness.academy.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import sk.ness.academy.domain.Article;
import sk.ness.academy.repository.ArticleRepository;

import java.util.List;

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
}
