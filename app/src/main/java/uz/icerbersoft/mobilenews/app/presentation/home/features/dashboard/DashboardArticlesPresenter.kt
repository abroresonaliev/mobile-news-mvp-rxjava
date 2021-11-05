package uz.icerbersoft.mobilenews.app.presentation.home.features.dashboard

import io.reactivex.observers.DisposableObserver
import kotlinx.coroutines.launch
import moxy.MvpPresenter
import moxy.presenterScope
import uz.icerbersoft.mobilenews.app.global.router.GlobalRouter
import uz.icerbersoft.mobilenews.data.model.article.Article
import uz.icerbersoft.mobilenews.data.model.article.ArticleListWrapper
import uz.icerbersoft.mobilenews.domain.interactor.article.detail.model.ArticleWrapper.*
import uz.icerbersoft.mobilenews.domain.interactor.article.list.ArticleListInteractor
import javax.inject.Inject

internal class DashboardArticlesPresenter @Inject constructor(
    private val interactor: ArticleListInteractor,
    private val router: GlobalRouter
) : MvpPresenter<DashboardArticlesView>() {

    override fun onFirstViewAttach() {
        getBreakingArticles()
        getTopArticles()
    }

    fun getBreakingArticles() {
        presenterScope.launch {
            interactor
                .getBreakingArticles()
                .doOnSubscribe { viewState.onDefinedBreakingArticleWrappers(listOf(LoadingItem)) }
                .subscribeWith(object : DisposableObserver<ArticleListWrapper>() {
                    override fun onNext(value: ArticleListWrapper) {
                        if (value.articles.isNotEmpty()) {
                            viewState.onDefinedBreakingArticleWrappers(
                                value.articles.map { ArticleItem(it) }
                            )
                        } else viewState.onDefinedBreakingArticleWrappers(listOf(EmptyItem))
                    }

                    override fun onError(throwable: Throwable) =
                        viewState.onDefinedBreakingArticleWrappers(listOf(ErrorItem))

                    override fun onComplete() {}
                })
        }
    }

    fun getTopArticles() {
        presenterScope.launch {
            interactor
                .getTopArticles()
                .doOnSubscribe { viewState.onDefinedTopArticleWrappers(listOf(LoadingItem)) }
                .subscribeWith(object : DisposableObserver<ArticleListWrapper>() {
                    override fun onNext(value: ArticleListWrapper) {
                        if (value.articles.isNotEmpty()) {
                            viewState.onDefinedTopArticleWrappers(
                                value.articles.map { ArticleItem(it) }
                            )
                        } else viewState.onDefinedTopArticleWrappers(listOf(EmptyItem))
                    }

                    override fun onError(throwable: Throwable) =
                        viewState.onDefinedTopArticleWrappers(listOf(ErrorItem))

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
        router.openNewsDetail(article.articleId)
    }
}