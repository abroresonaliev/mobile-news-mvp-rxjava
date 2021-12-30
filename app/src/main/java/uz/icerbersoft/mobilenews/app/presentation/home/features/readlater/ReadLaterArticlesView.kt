package uz.icerbersoft.mobilenews.app.presentation.home.features.readlater

import moxy.MvpView
import uz.icerbersoft.mobilenews.data.model.article.wrapper.ArticleWrapper

interface ReadLaterArticlesView : MvpView {

    fun onSuccessArticles(articles: List<ArticleWrapper>)
}