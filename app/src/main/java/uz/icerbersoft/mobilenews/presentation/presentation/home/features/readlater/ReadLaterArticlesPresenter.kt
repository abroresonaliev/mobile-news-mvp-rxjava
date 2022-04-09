package uz.icerbersoft.mobilenews.presentation.presentation.home.features.readlater

import io.reactivex.android.schedulers.AndroidSchedulers
import uz.icerbersoft.mobilenews.domain.data.entity.article.Article
import uz.icerbersoft.mobilenews.domain.data.entity.article.ArticleWrapper.*
import uz.icerbersoft.mobilenews.domain.usecase.article.readlater.ReadLaterArticlesUseCase
import uz.icerbersoft.mobilenews.presentation.global.router.GlobalRouter
import uz.icerbersoft.mobilenews.presentation.presentation.home.router.HomeRouter
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
            .doOnSubscribe { viewState.onSuccessArticles(listOf(LoadingItem)) }
            .subscribe(
                { value ->
                    val wrappers =
                        if (value.articles.isNotEmpty()) value.articles.map { ArticleItem(it) }
                        else listOf(EmptyItem)

                    viewState.onSuccessArticles(wrappers)
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