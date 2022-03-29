package uz.icerbersoft.mobilenews.domain.usecase.article.recommended

import io.reactivex.Observable
import uz.icerbersoft.mobilenews.domain.data.model.article.Article
import uz.icerbersoft.mobilenews.domain.data.model.article.ArticleListWrapper

interface RecommendedArticlesUseCase {

    fun getRecommendedArticles(): Observable<ArticleListWrapper>

    fun updateBookmark(article: Article): Observable<Unit>
}