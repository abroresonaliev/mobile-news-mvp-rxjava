package uz.icerbersoft.mobilenews.domain.usecase.article.dashboard

import io.reactivex.Observable
import uz.icerbersoft.mobilenews.domain.data.model.article.Article
import uz.icerbersoft.mobilenews.domain.data.model.article.ArticleListWrapper

interface DashboardArticlesUseCase {

    fun getBreakingArticles(): Observable<ArticleListWrapper>

    fun getTopArticles(): Observable<ArticleListWrapper>

    fun updateBookmark(article: Article): Observable<Unit>
}