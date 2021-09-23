package uz.icerbersoft.mobilenews.domain.interactor.article.detail

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import uz.icerbersoft.mobilenews.data.model.article.Article
import uz.icerbersoft.mobilenews.data.repository.article.ArticleRepository
import javax.inject.Inject

@Suppress("EXPERIMENTAL_API_USAGE")
class ArticleDetailInteractor @Inject constructor(
    private val articleRepository: ArticleRepository
) {

    fun getArticle(articleId: Long): Observable<Article> {
        return articleRepository.getArticle(articleId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun updateBookmark(article: Article): Observable<Unit> {
        return articleRepository.updateBookmark(article.articleId, !article.isBookmarked)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}