package uz.icerbersoft.mobilenews.domain.interactor.article.list

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import uz.icerbersoft.mobilenews.data.model.article.Article
import uz.icerbersoft.mobilenews.data.model.article.ArticleListWrapper
import uz.icerbersoft.mobilenews.data.repository.article.ArticleRepository
import javax.inject.Inject

@Suppress("EXPERIMENTAL_API_USAGE")
class ArticleListInteractor @Inject constructor(
    private val articleRepository: ArticleRepository
) {
    fun getArticles(): Observable<ArticleListWrapper> {
        return articleRepository.getArticles()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getBreakingArticles(): Observable<ArticleListWrapper> {
        return articleRepository.getBreakingNewsArticles()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getTopArticles(): Observable<ArticleListWrapper> {
        return articleRepository.getTopArticles()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getRecommendedArticles(): Observable<ArticleListWrapper> {
        return articleRepository.getRecommendedArticles()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getReadLaterArticles(): Observable<ArticleListWrapper> {
        return articleRepository.getReadLaterArticles()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun updateBookmark(article: Article): Observable<Unit> {
        return articleRepository.updateBookmark(article.articleId, !article.isBookmarked)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}