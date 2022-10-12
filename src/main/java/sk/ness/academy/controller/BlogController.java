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
import sk.ness.academy.dto.AuthorStatsInt;
import sk.ness.academy.exception.BlogException;
import sk.ness.academy.repository.ArticleRepository;
import sk.ness.academy.repository.CommentRepository;
import sk.ness.academy.service.ArticleService;
import sk.ness.academy.service.AuthorService;
import sk.ness.academy.service.CommentService;

import javax.annotation.Resource;

import static org.springframework.data.jpa.domain.Specification.where;
import static sk.ness.academy.repository.ArticleRepository.*;

@RestController
public class BlogController {

  @Autowired
  private ArticleService articleService;

  @Autowired
  private CommentService commentService;

  @Autowired
  private AuthorService authorService;




  // ~~ Article
  @RequestMapping(value = "articles", method = RequestMethod.GET)
  public List<Article> getAllArticles() {
	  return this.articleService.findAll();
  }

  @RequestMapping(value = "articles/{articleId}", method = RequestMethod.GET)
  public ArticleWithComments getArticle(@PathVariable final Integer articleId) {

      ArticleWithComments article = new ArticleWithComments();

      Article art = this.articleService.findById(articleId);

      article.setArticle(art);
      article.setComments(this.commentService.findByArticleId(articleId));
	  return article;
  }

  @RequestMapping(value = "articles/search/{searchText}", method = RequestMethod.GET)
  public List<Article> searchArticle(@PathVariable final String searchText) {
      return this.articleService.findAll(searchText);
  }

  @RequestMapping(value = "articles", method = RequestMethod.PUT)
  public void addArticle(@RequestBody(required=false) final Article article) {
    articleService.save(article);
  }

  // ~~ Author
  @RequestMapping(value = "authors", method = RequestMethod.GET)
  public List<Author> getAllAuthors() {
      ModelMapper modelMapper = new ModelMapper();
	  return this.articleService.findAll()
              .stream()
              .map(article -> modelMapper.map(article, Author.class))
              .collect(Collectors.toList());
  }


  @RequestMapping(value = "authors/stats", method = RequestMethod.GET)
  public List<AuthorStats> authorStats() {
        return this.authorService.getCountByAuthor();
  }



  @RequestMapping(value = "articles/{articleId}", method = RequestMethod.DELETE)
  public void deleteArticle(@PathVariable final Integer articleId) {
      this.articleService.deleteArticle(articleId);
  }

  // ~~ Comment

  @RequestMapping(value = "articles/{articleId}/comments", method = RequestMethod.GET)
  public List<Comment> getAllComments(@PathVariable final Integer articleId) {
    return this.commentService.findByArticleId(articleId);
  }

  @RequestMapping(value = "articles/{articleId}/comments", method = RequestMethod.PUT)
  public void addComment(@RequestBody(required=false) final Comment comment, @PathVariable final Integer articleId) {
    try {
      Article article = articleService.findById(articleId);
      comment.setArticle(article);
      commentService.save(comment);
    }
    catch (Exception e) {
      throw new BlogException("Article " + articleId + " not found", HttpStatus.NOT_FOUND);
    }
  }

  @RequestMapping(value = "comments/{commentId}", method = RequestMethod.DELETE)
  public void deleteComment(@PathVariable final Integer commentId) {
    commentService.deleteById(commentId);
  }

  @RequestMapping(value = "comments/{commentId}", method = RequestMethod.GET)
  public Comment getComment(@PathVariable final Integer commentId) {
    return this.commentService.findById(commentId);
  }
}
