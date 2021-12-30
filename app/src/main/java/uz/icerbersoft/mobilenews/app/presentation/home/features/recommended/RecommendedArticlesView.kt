package uz.icerbersoft.mobilenews.app.presentation.home.features.recommended

import moxy.MvpView
import uz.icerbersoft.mobilenews.data.model.article.wrapper.ArticleWrapper

interface RecommendedArticlesView : MvpView {

    fun onSuccessArticles(articles: List<ArticleWrapper>)
}