package uz.icerbersoft.mobilenews.presentation.presentation.detail

import io.reactivex.observers.DisposableObserver
import kotlinx.coroutines.launch
import moxy.MvpPresenter
import moxy.presenterScope
import uz.icerbersoft.mobilenews.presentation.presentation.detail.router.ArticleDetailRouter
import uz.icerbersoft.mobilenews.domain.data.model.article.Article
import uz.icerbersoft.mobilenews.domain.usecase.article.detail.ArticleDetailUseCase
import javax.inject.Inject
import kotlin.properties.Delegates

class ArticleDetailPresenter @Inject constructor(
    private val useCase: ArticleDetailUseCase,
    private val router: ArticleDetailRouter
) : MvpPresenter<ArticleDetailView>() {

    private var currentArticleId: String by Delegates.notNull()

    fun setArticleId(value: String) {
        currentArticleId = value
    }

    override fun onFirstViewAttach() =
        getArticleDetail()

    fun getArticleDetail() {
        presenterScope.launch {
            useCase.getArticle(currentArticleId)
                .subscribeWith(object : DisposableObserver<Article>() {
                    override fun onNext(value: Article) =
                        viewState.onSuccessArticleDetail(value)

                    override fun onError(throwable: Throwable) {}
//                        viewState.onFailureArticleDetail(throwable)

                    override fun onComplete() {}
                })
        }
    }

    fun updateBookmark(article: Article) {
        presenterScope.launch {
            useCase
                .updateBookmark(article)
                .subscribe()
        }
    }

    fun back() = router.back()
}