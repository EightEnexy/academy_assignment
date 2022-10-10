package sk.ness.academy.dto;

import sk.ness.academy.domain.Article;
import sk.ness.academy.domain.Comment;

import java.util.List;

public class ArticleWithComments {
    private Article article;
    private List<Comment> comments;

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
