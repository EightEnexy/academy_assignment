package sk.ness.academy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.ness.academy.domain.Article;
import sk.ness.academy.dto.AuthorStats;
import sk.ness.academy.dto.AuthorStatsInt;
import sk.ness.academy.repository.ArticleRepository;
import sk.ness.academy.repository.AuthorRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class AuthorServiceImpl implements AuthorService {

    @Autowired
    AuthorRepository authorRepository;

    public List<Article> getCountByAuthor() {
        return this.authorRepository.getCountByAuthor();
    }
}
