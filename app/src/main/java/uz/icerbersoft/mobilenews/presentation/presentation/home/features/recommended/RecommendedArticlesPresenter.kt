package uz.icerbersoft.mobilenews.presentation.presentation.home.features.recommended

import kotlinx.coroutines.launch
import moxy.MvpPresenter
import moxy.presenterScope
import uz.icerbersoft.mobilenews.presentation.global.router.GlobalRouter
import uz.icerbersoft.mobilenews.presentation.presentation.home.router.HomeRouter
import uz.icerbersoft.mobilenews.domain.data.model.article.Article
import uz.icerbersoft.mobilenews.domain.data.model.article.ArticleWrapper.*
import uz.icerbersoft.mobilenews.domain.usecase.article.recommended.RecommendedArticlesUseCase
import javax.inject.Inject

internal class RecommendedArticlesPresenter @Inject constructor(
    private val globalRouter: GlobalRouter,
    private val homeRouter: HomeRouter,
    private val useCase: RecommendedArticlesUseCase
) : MvpPresenter<RecommendedArticlesView>() {

    override fun onFirstViewAttach() =
        getRecommendedArticles()

    fun getRecommendedArticles() {
        presenterScope.launch {
            useCase
                .getRecommendedArticles()
                .doOnSubscribe { viewState.onSuccessArticles(listOf(LoadingItem)) }
                .subscribe(
                    { value ->
                        if (value.articles.isNotEmpty()) {
                            val articleItems = value.articles.map { ArticleItem(it) }
                            viewState.onSuccessArticles(articleItems)
                        } else
                            viewState.onSuccessArticles(listOf(EmptyItem))
                    },
                    { viewState.onSuccessArticles(listOf(ErrorItem)) }
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
        globalRouter.openNewsDetail(article.articleId)

    fun back() =
        homeRouter.openDashboardTab(true)
}