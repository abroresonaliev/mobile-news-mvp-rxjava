package uz.icerbersoft.mobilenews.presentation.presentation.home.features.dashboard

import moxy.MvpView
import uz.icerbersoft.mobilenews.domain.data.model.article.ArticleWrapper

interface DashboardArticlesView : MvpView {

    fun onDefinedBreakingArticleWrappers(articles: List<ArticleWrapper>)

    fun onDefinedTopArticleWrappers(articles: List<ArticleWrapper>)
}