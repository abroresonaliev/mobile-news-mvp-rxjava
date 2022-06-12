package uz.icerbersoft.mobilenews.presentation.presentation.home.features.dashboard

import moxy.MvpView
import uz.icerbersoft.mobilenews.domain.data.entity.article.Article
import uz.icerbersoft.mobilenews.presentation.support.event.LoadingListEvent

interface DashboardArticlesView : MvpView {

    fun onDefinedBreakingArticleWrappers(articles: LoadingListEvent<Article>)

    fun onDefinedTopArticleWrappers(articles: LoadingListEvent<Article>)
}