package uz.icerbersoft.mobilenews.domain.usecase.common.bookmark

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import uz.icerbersoft.mobilenews.data.model.article.Article
import uz.icerbersoft.mobilenews.data.repository.article.ArticleRepository
import javax.inject.Inject

class BookmarkUseCase @Inject constructor(
    private val articleRepository: ArticleRepository
) {

    fun updateBookmark(article: Article): Observable<Unit> {
        return articleRepository.updateBookmark(article.articleId, !article.isBookmarked)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}