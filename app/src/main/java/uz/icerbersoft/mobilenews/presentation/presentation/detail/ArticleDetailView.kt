package uz.icerbersoft.mobilenews.presentation.presentation.detail

import moxy.MvpView
import uz.icerbersoft.mobilenews.domain.data.model.article.Article

interface ArticleDetailView : MvpView {

    fun onSuccessArticleDetail(article: Article)
}