package uz.icerbersoft.mobilenews.presentation.presentation.home.features.readlater

import moxy.MvpView
import uz.icerbersoft.mobilenews.domain.data.entity.article.Article
import uz.icerbersoft.mobilenews.presentation.support.event.LoadingListEvent

interface ReadLaterArticlesView : MvpView {

    fun onSuccessArticles(articles: LoadingListEvent<Article>)
}