package uz.icerbersoft.mobilenews.presentation.presentation.home.features.recommended

import uz.icerbersoft.mobilenews.domain.data.entity.article.Article
import uz.icerbersoft.mobilenews.domain.data.entity.article.ArticleWrapper.*
import uz.icerbersoft.mobilenews.domain.usecase.article.recommended.RecommendedArticlesUseCase
import uz.icerbersoft.mobilenews.presentation.global.router.GlobalRouter
import uz.icerbersoft.mobilenews.presentation.presentation.home.router.HomeRouter
import uz.icerbersoft.mobilenews.presentation.support.moxy.BaseMoxyPresenter
import javax.inject.Inject

internal class RecommendedArticlesPresenter @Inject constructor(
    private val globalRouter: GlobalRouter,
    private val homeRouter: HomeRouter,
    private val useCase: RecommendedArticlesUseCase
) : BaseMoxyPresenter<RecommendedArticlesView>() {

    override fun onFirstViewAttach() =
        getRecommendedArticles()

    fun getRecommendedArticles() {
        val disposable = useCase.getRecommendedArticles()
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

        compositeDisposable.add(disposable)
    }

    fun updateBookmark(article: Article) {
        val disposable = useCase.updateBookmark(article)
            .subscribe()

        compositeDisposable.add(disposable)
    }

    fun openArticleDetail(article: Article) =
        globalRouter.openNewsDetail(article.articleId)

    fun back() =
        homeRouter.openDashboardTab(true)
}