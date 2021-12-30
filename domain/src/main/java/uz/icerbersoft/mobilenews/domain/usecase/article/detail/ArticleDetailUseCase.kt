package uz.icerbersoft.mobilenews.domain.usecase.article.detail

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import uz.icerbersoft.mobilenews.data.model.article.Article
import uz.icerbersoft.mobilenews.data.repository.article.ArticleRepository
import uz.icerbersoft.mobilenews.domain.usecase.common.bookmark.BookmarkUseCase
import javax.inject.Inject

class ArticleDetailUseCase @Inject constructor(
    private val articleRepository: ArticleRepository,
    private val bookmarkUseCase: BookmarkUseCase
) {

    fun getArticle(articleId: String): Observable<Article> {
        return articleRepository.getArticle(articleId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun updateBookmark(article: Article): Observable<Unit> {
        return bookmarkUseCase.updateBookmark(article)
    }
}