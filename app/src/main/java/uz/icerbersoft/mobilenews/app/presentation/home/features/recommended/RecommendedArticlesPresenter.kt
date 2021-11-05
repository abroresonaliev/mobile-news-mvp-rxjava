package uz.icerbersoft.mobilenews.app.presentation.home.features.recommended

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import kotlinx.coroutines.launch
import moxy.MvpPresenter
import moxy.presenterScope
import uz.icerbersoft.mobilenews.app.global.router.GlobalRouter
import uz.icerbersoft.mobilenews.app.presentation.home.router.HomeRouter
import uz.icerbersoft.mobilenews.data.model.article.Article
import uz.icerbersoft.mobilenews.data.model.article.ArticleListWrapper
import uz.icerbersoft.mobilenews.domain.interactor.article.detail.model.ArticleWrapper.*
import uz.icerbersoft.mobilenews.domain.interactor.article.list.ArticleListInteractor
import javax.inject.Inject

internal class RecommendedArticlesPresenter @Inject constructor(
    private val interactor: ArticleListInteractor,
    private val globalRouter: GlobalRouter,
    private val homeRouter: HomeRouter
) : MvpPresenter<RecommendedArticlesView>() {

    override fun onFirstViewAttach() =
        getRecommendedArticles()

    fun getRecommendedArticles() {
        presenterScope.launch {
            interactor
                .getRecommendedArticles()
                .doOnSubscribe { viewState.onSuccessArticles(listOf(LoadingItem)) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<ArticleListWrapper>() {
                    override fun onNext(value: ArticleListWrapper) {
                        if (value.articles.isNotEmpty()) {
                            val articleItems = value.articles.map { ArticleItem(it) }
                            viewState.onSuccessArticles(articleItems)
                        } else
                            viewState.onSuccessArticles(listOf(EmptyItem))
                    }

                    override fun onError(throwable: Throwable) =
                        viewState.onSuccessArticles(listOf(ErrorItem))

                    override fun onComplete() {}
                })
        }
    }

    fun updateBookmark(article: Article) {
        presenterScope.launch {
            interactor
                .updateBookmark(article)
                .subscribe()
        }
    }

    fun openArticleDetail(article: Article) {
        globalRouter.openNewsDetail(article.articleId)
    }

    fun back() = homeRouter.openDashboardTab(true)
}