package uz.icerbersoft.mobilenews.presentation.presentation.home.features.dashboard

import uz.icerbersoft.mobilenews.domain.data.entity.article.Article
import uz.icerbersoft.mobilenews.domain.usecase.article.dashboard.DashboardArticlesUseCase
import uz.icerbersoft.mobilenews.presentation.global.router.GlobalRouter
import uz.icerbersoft.mobilenews.presentation.support.event.LoadingListEvent.*
import uz.icerbersoft.mobilenews.presentation.support.moxy.BaseMoxyPresenter
import javax.inject.Inject

internal class DashboardArticlesPresenter @Inject constructor(
    private val router: GlobalRouter,
    private val useCase: DashboardArticlesUseCase
) : BaseMoxyPresenter<DashboardArticlesView>() {

    override fun onFirstViewAttach() {
        getBreakingArticles()
        getTopArticles()
    }

    fun getBreakingArticles() {
        val disposable = useCase.getBreakingArticles()
            .doOnSubscribe { viewState.onDefinedBreakingArticleWrappers(LoadingState) }
            .subscribe(
                { value ->
                    val wrappers =
                        if (value.articles.isNotEmpty()) SuccessState(value.articles)
                        else EmptyState

                    viewState.onDefinedBreakingArticleWrappers(wrappers)
                },
                { viewState.onDefinedBreakingArticleWrappers(ErrorState(it.localizedMessage)) }
            )

        compositeDisposable.add(disposable)
    }

    fun getTopArticles() {
        val disposable = useCase.getTopArticles()
            .doOnSubscribe { viewState.onDefinedTopArticleWrappers(LoadingState) }
            .subscribe(
                { value ->
                    val wrappers =
                        if (value.articles.isNotEmpty()) SuccessState(value.articles)
                        else EmptyState

                    viewState.onDefinedTopArticleWrappers(wrappers)
                },
                { viewState.onDefinedTopArticleWrappers(ErrorState(it.localizedMessage)) }
            )

        compositeDisposable.add(disposable)
    }

    fun updateBookmark(article: Article) {
        val disposable = useCase.updateBookmark(article)
            .subscribe()

        compositeDisposable.add(disposable)
    }

    fun openArticleDetail(article: Article) =
        router.openNewsDetail(article.articleId)

    fun openSettingsScreen() =
        router.openSettingsScreen()
}