package sk.ness.academy.controller;

import java.util.List;
import java.util.*;

import javax.annotation.Resource;

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
import sk.ness.academy.service.ArticleService;
import sk.ness.academy.service.AuthorService;
import sk.ness.academy.service.CommentService;

@RestController
public class BlogController {

  @Resource
  private ArticleService articleService;

  @Resource
  private AuthorService authorService;

  @Resource
  private CommentService commentService;

  private boolean isValidArticle(Article article) {
    return article != null;
  }

  private boolean isValidComment(Comment comment) {
    return comment != null;
  }


  // ~~ Article
  @RequestMapping(value = "articles", method = RequestMethod.GET)
  public List<Article> getAllArticles() {
	  return this.articleService.findAll();
  }

  @RequestMapping(value = "articles/{articleId}", method = RequestMethod.GET)
  public ArticleWithComments getArticle(@PathVariable final Integer articleId) {
      ArticleWithComments article = new ArticleWithComments();

      Article art = this.articleService.findByID(articleId);
      if (!isValidArticle(art)) {
        throw new BlogException("Article " + articleId + " not found", HttpStatus.NOT_FOUND);
      }

      article.setArticle(art);
      article.setComments(this.commentService.getAllComments(articleId));
	  return article;
  }

  @RequestMapping(value = "articles/search/{searchText}", method = RequestMethod.GET)
  public List<Article> searchArticle(@PathVariable final String searchText) {
      return this.articleService.searchByText(searchText);
  }

  @RequestMapping(value = "articles", method = RequestMethod.PUT)
  public void addArticle(@RequestBody(required=false) final Article article) {
    if (!isValidArticle(article)) {
      throw new BlogException("Required request body is missing", HttpStatus.BAD_REQUEST);
    }
    this.articleService.createArticle(article);
  }


  // ~~ Author
  @RequestMapping(value = "authors", method = RequestMethod.GET)
  public List<Author> getAllAuthors() {
	  return this.authorService.findAll();
  }

  @RequestMapping(value = "authors/stats", method = RequestMethod.GET)
  public List<AuthorStats> authorStats() {
        return this.authorService.getAuthorStats();
  }

  @RequestMapping(value = "articles/{articleId}", method = RequestMethod.DELETE)
  public void deleteArticle(@PathVariable final Integer articleId) {
    try {
      this.commentService.deleteAllCommentsByID(articleId);
      this.articleService.deleteByID(articleId);
    }
    catch(IllegalArgumentException e) {
      throw new BlogException("Article " + articleId + "not found", HttpStatus.NOT_FOUND);
    }
  }

  // ~~ Comment

  @RequestMapping(value = "articles/{articleId}/comments", method = RequestMethod.GET)
  public List<Comment> getAllComments(@PathVariable final Integer articleId) {
    return this.commentService.getAllComments(articleId);
  }

  @RequestMapping(value = "articles/{articleId}/comments", method = RequestMethod.PUT)
  public void addComment(@RequestBody(required=false) final Comment comment, @PathVariable final Integer articleId) {
    Article article = articleService.findByID(articleId);
    if (!isValidArticle(article)) {
      throw new BlogException("Article " + articleId + " not found", HttpStatus.NOT_FOUND);
    }
    if(!isValidComment(comment)) {
      throw new BlogException("Required request body is missing", HttpStatus.BAD_REQUEST);
    }
    comment.setArticle(article);
    commentService.addComment(comment);
  }

  @RequestMapping(value = "comments/{commentId}", method = RequestMethod.DELETE)
  public void deleteComment(@PathVariable final Integer commentId) {
    try {
      this.commentService.deleteByID(commentId);
    }
    catch(IllegalArgumentException e) {
      throw new BlogException("Comment " + commentId + " not found", HttpStatus.NOT_FOUND);
    }
  }

  @RequestMapping(value = "comments/{commentId}", method = RequestMethod.GET)
  public Comment getComment(@PathVariable final Integer commentId) {
    Comment comment = this.commentService.findByID(commentId);
    if (!isValidComment(comment)) {
      throw new BlogException("Comment " + commentId + " not found", HttpStatus.NOT_FOUND);
    }
    return comment;
  }

}
