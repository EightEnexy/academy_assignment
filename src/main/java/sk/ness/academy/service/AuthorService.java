package sk.ness.academy.service;

import sk.ness.academy.domain.Article;
import sk.ness.academy.dto.AuthorStats;
import sk.ness.academy.dto.AuthorStatsInt;

import java.util.List;

public interface AuthorService {

    List <AuthorStats> getCountByAuthor();
}
