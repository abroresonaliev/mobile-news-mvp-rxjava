package uz.icerbersoft.mobilenews.app.presentation.detail

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import kotlinx.coroutines.launch
import moxy.MvpPresenter
import moxy.presenterScope
import uz.icerbersoft.mobilenews.app.presentation.detail.router.ArticleDetailRouter
import uz.icerbersoft.mobilenews.data.model.article.Article
import uz.icerbersoft.mobilenews.domain.interactor.article.detail.ArticleDetailInteractor
import javax.inject.Inject
import kotlin.properties.Delegates

class ArticleDetailPresenter @Inject constructor(
    private val interactor: ArticleDetailInteractor,
    private val router: ArticleDetailRouter
) : MvpPresenter<ArticleDetailView>() {

    private var currentArticleId: Long by Delegates.notNull()

    fun setArticleId(value: Long) {
        currentArticleId = value
    }

    override fun onFirstViewAttach() {
        getArticleDetail()
    }

    fun getArticleDetail() {
        presenterScope.launch {
            interactor
                .getArticle(currentArticleId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<Article>() {
                    override fun onNext(value: Article) =
                        viewState.onSuccessArticleDetail(value)

                    override fun onError(throwable: Throwable) {}
//                        viewState.onFailureArticleDetail(throwable)

                    override fun onComplete() {}
                })
        }
    }

    fun back() = router.back()

    fun updateBookmark(article: Article) {
        presenterScope.launch {
            interactor
                .updateBookmark(article)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        }
    }
}