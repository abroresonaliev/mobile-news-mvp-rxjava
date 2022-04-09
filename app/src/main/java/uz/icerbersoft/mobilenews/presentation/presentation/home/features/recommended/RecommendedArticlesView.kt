package uz.icerbersoft.mobilenews.presentation.presentation.home.features.recommended

import moxy.MvpView
import uz.icerbersoft.mobilenews.domain.data.entity.article.ArticleWrapper

interface RecommendedArticlesView : MvpView {

    fun onSuccessArticles(articles: List<ArticleWrapper>)
}