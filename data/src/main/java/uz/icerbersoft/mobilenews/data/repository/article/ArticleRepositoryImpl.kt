package uz.icerbersoft.mobilenews.data.repository.article

import io.reactivex.Observable
import kotlinx.coroutines.FlowPreview
import uz.icerbersoft.mobilenews.data.datasource.database.dao.article.ArticleEntityDao
import uz.icerbersoft.mobilenews.data.datasource.rest.service.RestService
import uz.icerbersoft.mobilenews.data.mapper.mapToArticle
import uz.icerbersoft.mobilenews.data.mapper.mapToArticleEntity
import uz.icerbersoft.mobilenews.data.model.article.Article
import uz.icerbersoft.mobilenews.data.model.article.ArticleListWrapper
import java.net.ConnectException

internal class ArticleRepositoryImpl(
    private val articleEntityDao: ArticleEntityDao,
    private val restService: RestService
) : ArticleRepository {

    override fun getArticle(articleId: Long): Observable<Article> {
        return articleEntityDao.getArticleEntityById(articleId).map { it.mapToArticle() }
    }

    @FlowPreview
    override fun getArticles(): Observable<ArticleListWrapper> {
        return restService.getBreakingArticles()
            .doOnNext { it -> it.articles.forEach { articleEntityDao.upsert(it.mapToArticleEntity()) } }
            .map { it.articles.isNotEmpty() }
            .doOnError {
                if (it is ConnectException) Observable.just(false)
                else throw it
            }
            .flatMap { isLoaded ->
                articleEntityDao.getArticleEntities()
                    .map { list -> list.map { it.mapToArticle() } }
                    .map { ArticleListWrapper(it, !isLoaded) }
            }
    }

    @FlowPreview
    override fun getBreakingNewsArticles(): Observable<ArticleListWrapper> {
        return restService.getBreakingArticles()
            .doOnNext { it -> it.articles.forEach { articleEntityDao.upsert(it.mapToArticleEntity()) } }
            .map { it.articles.isNotEmpty() }
            .doOnError {
                if (it is ConnectException) Observable.just(false)
                else throw it
            }
            .flatMap { isLoaded ->
                articleEntityDao.getArticleEntities()
                    .map { list -> list.map { it.mapToArticle() } }
                    .map { ArticleListWrapper(it, !isLoaded) }
            }
    }

    @FlowPreview
    override fun getTopArticles(): Observable<ArticleListWrapper> {
        return restService.getTopArticles()
            .doOnNext { it -> it.articles.forEach { articleEntityDao.upsert(it.mapToArticleEntity()) } }
            .map { it.articles.isNotEmpty() }
            .doOnError {
                if (it is ConnectException) Observable.just(false)
                else throw it
            }
            .flatMap { isLoaded ->
                articleEntityDao.getArticleEntities()
                    .map { list -> list.map { it.mapToArticle() } }
                    .map { ArticleListWrapper(it, !isLoaded) }
            }
    }

    @FlowPreview
    override fun getRecommendedArticles(): Observable<ArticleListWrapper> {
        return restService.getRecommendedArticles()
            .doOnNext { it -> it.articles.forEach { articleEntityDao.upsert(it.mapToArticleEntity()) } }
            .map { it.articles.isNotEmpty() }
            .doOnError {
                if (it is ConnectException) Observable.just(false)
                else throw it
            }
            .flatMap { isLoaded ->
                articleEntityDao.getArticleEntities()
                    .map { list -> list.map { it.mapToArticle() } }
                    .map { ArticleListWrapper(it, !isLoaded) }
            }
    }

    @FlowPreview
    override fun getReadLaterArticles(): Observable<ArticleListWrapper> {
        return articleEntityDao.getArticleEntitiesByBookmark(true)
            .map { list -> list.map { it.mapToArticle() } }
            .map { ArticleListWrapper(it, true) }
    }

    override fun updateBookmark(articleId: Long, isBookmarked: Boolean): Observable<Unit> {
        return Observable.create { articleEntityDao.updateBookmark(articleId, isBookmarked) }
    }
}