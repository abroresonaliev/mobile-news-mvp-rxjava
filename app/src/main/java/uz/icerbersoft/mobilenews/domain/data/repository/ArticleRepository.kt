package uz.icerbersoft.mobilenews.domain.data.repository

import io.reactivex.Observable
import uz.icerbersoft.mobilenews.domain.data.model.article.Article
import uz.icerbersoft.mobilenews.domain.data.model.article.ArticleListWrapper

interface ArticleRepository {

    fun getArticle(articleId: String): Observable<Article>

    fun getArticles(): Observable<ArticleListWrapper>

    fun getBreakingNewsArticles(): Observable<ArticleListWrapper>

    fun getTopArticles(): Observable<ArticleListWrapper>

    fun getRecommendedArticles(): Observable<ArticleListWrapper>

    fun getReadLaterArticles(): Observable<ArticleListWrapper>

    fun updateBookmark(articleId: String, isBookmarked: Boolean): Observable<Unit>
}