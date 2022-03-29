package uz.icerbersoft.mobilenews.domain.data.model.article

import uz.icerbersoft.mobilenews.domain.data.model.article.Article

data class ArticleListWrapper(val articles: List<Article>, val isFromOfflineSource: Boolean)
