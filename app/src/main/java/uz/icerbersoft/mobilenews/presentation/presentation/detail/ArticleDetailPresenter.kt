package uz.icerbersoft.mobilenews.presentation.presentation.detail

import uz.icerbersoft.mobilenews.domain.data.entity.article.Article
import uz.icerbersoft.mobilenews.domain.usecase.article.detail.ArticleDetailUseCase
import uz.icerbersoft.mobilenews.presentation.presentation.detail.router.ArticleDetailRouter
import uz.icerbersoft.mobilenews.presentation.support.event.LoadingEvent
import uz.icerbersoft.mobilenews.presentation.support.event.LoadingEvent.*
import uz.icerbersoft.mobilenews.presentation.support.moxy.BaseMoxyPresenter
import javax.inject.Inject
import kotlin.properties.Delegates

class ArticleDetailPresenter @Inject constructor(
    private val useCase: ArticleDetailUseCase,
    private val router: ArticleDetailRouter
) : BaseMoxyPresenter<ArticleDetailView>() {

    private var currentArticleId: String by Delegates.notNull()

    fun setArticleId(value: String) {
        currentArticleId = value
    }

    override fun onFirstViewAttach() =
        getArticleDetail()

    fun getArticleDetail() {
        val disposable = useCase.getArticle(currentArticleId)
            .doOnSubscribe { viewState.onSuccessArticleDetail(LoadingState) }
            .subscribe(
                { viewState.onSuccessArticleDetail(SuccessState(it)) },
                { viewState.onSuccessArticleDetail(ErrorState(it.localizedMessage)) }
            )

        compositeDisposable.add(disposable)
    }

    fun updateBookmark(article: Article) {
        val disposable = useCase.updateBookmark(article)
            .subscribe()

        compositeDisposable.add(disposable)
    }

    fun back() = router.back()
}