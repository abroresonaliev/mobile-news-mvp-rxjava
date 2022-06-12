package uz.icerbersoft.mobilenews.presentation.presentation.detail

import moxy.MvpView
import uz.icerbersoft.mobilenews.domain.data.entity.article.Article
import uz.icerbersoft.mobilenews.presentation.support.event.LoadingEvent

interface ArticleDetailView : MvpView {

    fun onSuccessArticleDetail(event: LoadingEvent<Article>)
}