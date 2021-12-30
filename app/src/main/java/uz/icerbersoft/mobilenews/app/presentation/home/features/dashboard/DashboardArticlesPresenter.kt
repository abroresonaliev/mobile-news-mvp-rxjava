package uz.icerbersoft.mobilenews.app.presentation.home.features.dashboard

import kotlinx.coroutines.launch
import moxy.MvpPresenter
import moxy.presenterScope
import uz.icerbersoft.mobilenews.app.global.router.GlobalRouter
import uz.icerbersoft.mobilenews.data.model.article.Article
import uz.icerbersoft.mobilenews.data.model.article.wrapper.ArticleWrapper.*
import uz.icerbersoft.mobilenews.domain.usecase.article.dashboard.DashboardArticlesUseCase
import javax.inject.Inject

internal class DashboardArticlesPresenter @Inject constructor(
    private val router: GlobalRouter,
    private val useCase: DashboardArticlesUseCase
) : MvpPresenter<DashboardArticlesView>() {

    override fun onFirstViewAttach() {
        getBreakingArticles()
        getTopArticles()
    }

    fun getBreakingArticles() {
        presenterScope.launch {
            useCase
                .getBreakingArticles()
                .doOnSubscribe { viewState.onDefinedBreakingArticleWrappers(listOf(LoadingItem)) }
                .subscribe(
                    { value ->
                        val wrappers =
                            if (value.articles.isNotEmpty()) value.articles.map { ArticleItem(it) }
                            else listOf(EmptyItem)

                        viewState.onDefinedBreakingArticleWrappers(wrappers)
                    },
                    { viewState.onDefinedBreakingArticleWrappers(listOf(ErrorItem)) }
                )
        }
    }

    fun getTopArticles() {
        presenterScope.launch {
            useCase
                .getTopArticles()
                .doOnSubscribe { viewState.onDefinedTopArticleWrappers(listOf(LoadingItem)) }
                .subscribe(
                    { value ->
                        val wrappers =
                            if (value.articles.isNotEmpty()) value.articles.map { ArticleItem(it) }
                            else listOf(EmptyItem)

                        viewState.onDefinedTopArticleWrappers(wrappers)
                    },
                    { viewState.onDefinedTopArticleWrappers(listOf(ErrorItem)) }
                )
        }
    }

    fun updateBookmark(article: Article) {
        presenterScope.launch {
            useCase
                .updateBookmark(article)
                .subscribe()

        }
    }

    fun openArticleDetail(article: Article) =
        router.openNewsDetail(article.articleId)
}