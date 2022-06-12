package uz.icerbersoft.mobilenews.presentation.presentation.home.features.readlater

import uz.icerbersoft.mobilenews.domain.data.entity.article.Article
import uz.icerbersoft.mobilenews.domain.usecase.article.readlater.ReadLaterArticlesUseCase
import uz.icerbersoft.mobilenews.presentation.global.router.GlobalRouter
import uz.icerbersoft.mobilenews.presentation.presentation.home.router.HomeRouter
import uz.icerbersoft.mobilenews.presentation.support.event.LoadingListEvent
import uz.icerbersoft.mobilenews.presentation.support.event.LoadingListEvent.*
import uz.icerbersoft.mobilenews.presentation.support.moxy.BaseMoxyPresenter
import javax.inject.Inject

internal class ReadLaterArticlesPresenter @Inject constructor(
    private val globalRouter: GlobalRouter,
    private val homeRouter: HomeRouter,
    private val useCase: ReadLaterArticlesUseCase
) : BaseMoxyPresenter<ReadLaterArticlesView>() {

    override fun onFirstViewAttach() =
        getReadLaterArticles()

    fun getReadLaterArticles() {
        val disposable = useCase.getReadLaterArticles()
            .doOnSubscribe { viewState.onSuccessArticles(LoadingState) }
            .subscribe(
                { value ->
                    val wrappers =
                        if (value.articles.isNotEmpty()) SuccessState(value.articles) 
                        else EmptyState

                    viewState.onSuccessArticles(wrappers)
                },
                { viewState.onSuccessArticles(ErrorState(it.localizedMessage)) }
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