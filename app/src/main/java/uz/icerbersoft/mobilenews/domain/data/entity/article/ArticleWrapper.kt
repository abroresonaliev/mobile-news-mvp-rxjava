package uz.icerbersoft.mobilenews.domain.data.entity.article

sealed class ArticleWrapper {

    object LoadingItem : ArticleWrapper()

    data class ArticleItem(val article: Article) : ArticleWrapper()

    object EmptyItem : ArticleWrapper()

    object ErrorItem : ArticleWrapper()
}