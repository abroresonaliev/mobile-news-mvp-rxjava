package uz.icerbersoft.mobilenews.domain.usecase.article.readlater

import io.reactivex.Observable
import uz.icerbersoft.mobilenews.domain.data.model.article.Article
import uz.icerbersoft.mobilenews.domain.data.model.article.ArticleListWrapper

interface ReadLaterArticlesUseCase {

    fun getReadLaterArticles(): Observable<ArticleListWrapper>

    fun updateBookmark(article: Article): Observable<Unit>
}