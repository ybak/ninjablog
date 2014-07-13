package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import models.Article;
import models.ArticleDto;
import models.ArticlesDto;
import models.User;

import com.google.inject.persist.Transactional;

public class ArticleDao extends BaseDao<Article> {

    @Transactional
    public ArticlesDto getAllArticles() {
        List<Article> articles = createQuery("FROM Article x", Article.class).getResultList();
        return new ArticlesDto(articles);
    }

    @Transactional
    public Article getFirstArticleForFrontPage() {
        TypedQuery<Article> query = createQuery("FROM Article ORDER BY postedAt DESC", Article.class);
        return first(query);
    }

    @Transactional
    public List<Article> getOlderArticlesForFrontPage() {
        TypedQuery<Article> q = createQuery("FROM Article ORDER BY postedAt DESC", Article.class);
        return subResult(q, 1, 10);
    }

    @Transactional
    public Article getArticle(Long id) {
        TypedQuery<Article> query = createQuery("SELECT x FROM Article x WHERE x.id = :idParam", Article.class);
        return query.setParameter("idParam", id).getSingleResult();
    }

    /**
     * Returns false if user cannot be found in database.
     */
    @Transactional
    public boolean postArticle(String username, ArticleDto articleDto) {

        EntityManager entityManager = em();

        Query query = entityManager.createQuery("SELECT x FROM User x WHERE username = :usernameParam");
        User user = (User) query.setParameter("usernameParam", username).getSingleResult();

        if (user == null) {
            return false;
        }

        Article article = new Article(user, articleDto.title, articleDto.content);
        entityManager.persist(article);

        return true;

    }

}
