package sk.ness.academy.controller;

import java.util.List;
import java.util.stream.Collectors;



import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import sk.ness.academy.domain.Article;
import sk.ness.academy.domain.Comment;
import sk.ness.academy.dto.ArticleWithComments;
import sk.ness.academy.dto.Author;
import sk.ness.academy.dto.AuthorStats;
import sk.ness.academy.exception.BlogException;
import sk.ness.academy.repository.ArticleRepository;
import sk.ness.academy.repository.CommentRepository;

import javax.annotation.Resource;

import static org.springframework.data.jpa.domain.Specification.where;
import static sk.ness.academy.repository.ArticleRepository.*;

@RestController
public class BlogController {

  @Autowired
  private ArticleRepository articleRepository;

  @Autowired
  private CommentRepository commentRepository;


  // ~~ Article
  @RequestMapping(value = "articles", method = RequestMethod.GET)
  public List<Article> getAllArticles() {
	  return this.articleRepository.findAll();
  }

  @RequestMapping(value = "articles/{articleId}", method = RequestMethod.GET)
  public ArticleWithComments getArticle(@PathVariable final Integer articleId) {
      ArticleWithComments article = new ArticleWithComments();

      Article art = this.articleRepository.findById(articleId).
              orElseThrow(()-> new BlogException("Article " + articleId + " not found", HttpStatus.NOT_FOUND));

      article.setArticle(art);
      article.setComments(this.commentRepository.findByArticleId(articleId));
	  return article;
  }

  @RequestMapping(value = "articles/search/{searchText}", method = RequestMethod.GET)
  public List<Article> searchArticle(@PathVariable final String searchText) {
      return this.articleRepository.findAll(
              where(titleContains(searchText)).or(textContains(searchText)).or(authorContains(searchText)));
  }

  @RequestMapping(value = "articles", method = RequestMethod.PUT)
  public void addArticle(@RequestBody(required=false) final Article article) {
    articleRepository.save(article);
  }

  // ~~ Author
  @RequestMapping(value = "authors", method = RequestMethod.GET)
  public List<Author> getAllAuthors() {
      ModelMapper modelMapper = new ModelMapper();
	  return this.articleRepository.findAll()
              .stream()
              .map(article -> modelMapper.map(article, Author.class))
              .collect(Collectors.toList());
  }

  /*
  @RequestMapping(value = "authors/stats", method = RequestMethod.GET)
  public List<AuthorStats> authorStats() {
        return this.articleRepository.findAuthorStats();
  }
  */

  @RequestMapping(value = "articles/{articleId}", method = RequestMethod.DELETE)
  public void deleteArticle(@PathVariable final Integer articleId) {
    articleRepository.findById(articleId).map(article -> {
      articleRepository.delete(article);
      return ResponseEntity.ok().build();
    }).orElseThrow(() -> new BlogException("Article " + articleId + "not found", HttpStatus.NOT_FOUND));
  }

  // ~~ Comment

  @RequestMapping(value = "articles/{articleId}/comments", method = RequestMethod.GET)
  public List<Comment> getAllComments(@PathVariable final Integer articleId) {
    return this.commentRepository.findByArticleId(articleId);
  }

  @RequestMapping(value = "articles/{articleId}/comments", method = RequestMethod.PUT)
  public void addComment(@RequestBody(required=false) final Comment comment, @PathVariable final Integer articleId) {
    articleRepository.findById(articleId).map(article -> {
      comment.setArticle(article);
      return commentRepository.save(comment);
    }).orElseThrow(() -> new BlogException("Article " + articleId + " not found", HttpStatus.NOT_FOUND));
  }

  @RequestMapping(value = "comments/{commentId}", method = RequestMethod.DELETE)
  public void deleteComment(@PathVariable final Integer commentId) {
    commentRepository.deleteById(commentId);
  }

  @RequestMapping(value = "comments/{commentId}", method = RequestMethod.GET)
  public Comment getComment(@PathVariable final Integer commentId) {
    return commentRepository.findById(commentId)
            .orElseThrow(() -> new BlogException("Article " + commentId + " not found", HttpStatus.NOT_FOUND));
  }
}
