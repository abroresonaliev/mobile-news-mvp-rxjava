package uz.icerbersoft.mobilenews.presentation.presentation.home.features.readlater

import kotlinx.coroutines.launch
import moxy.MvpPresenter
import moxy.presenterScope
import uz.icerbersoft.mobilenews.presentation.global.router.GlobalRouter
import uz.icerbersoft.mobilenews.presentation.presentation.home.router.HomeRouter
import uz.icerbersoft.mobilenews.domain.data.model.article.Article
import uz.icerbersoft.mobilenews.domain.data.model.article.ArticleWrapper.*
import uz.icerbersoft.mobilenews.domain.usecase.article.readlater.ReadLaterArticlesUseCase
import javax.inject.Inject

internal class ReadLaterArticlesPresenter @Inject constructor(
    private val globalRouter: GlobalRouter,
    private val homeRouter: HomeRouter,
    private val useCase: ReadLaterArticlesUseCase
) : MvpPresenter<ReadLaterArticlesView>() {

    override fun onFirstViewAttach() =
        getReadLaterArticles()

    fun getReadLaterArticles() {
        presenterScope.launch {
            useCase
                .getReadLaterArticles()
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