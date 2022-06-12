package uz.icerbersoft.mobilenews.presentation.presentation.home.features.recommended

import moxy.MvpView
import uz.icerbersoft.mobilenews.domain.data.entity.article.Article
import uz.icerbersoft.mobilenews.presentation.support.event.LoadingListEvent

interface RecommendedArticlesView : MvpView {

    fun onSuccessArticles(event: LoadingListEvent<Article>)
}