package uz.icerbersoft.mobilenews.presentation.presentation.home.features.readlater

import moxy.MvpView
import uz.icerbersoft.mobilenews.domain.data.entity.article.ArticleWrapper

interface ReadLaterArticlesView : MvpView {

    fun onSuccessArticles(articles: List<ArticleWrapper>)
}