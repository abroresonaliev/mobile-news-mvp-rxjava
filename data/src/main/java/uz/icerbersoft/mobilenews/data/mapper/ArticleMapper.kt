package uz.icerbersoft.mobilenews.data.mapper

import uz.icerbersoft.mobilenews.data.model.article.Article
import uz.icerbersoft.mobilenews.data.model.article.ArticleEntity
import uz.icerbersoft.mobilenews.data.model.article.Source
import uz.icerbersoft.mobilenews.data.model.article.response.ArticleResponse
import uz.icerbersoft.mobilenews.data.model.article.response.SourceResponse
import uz.icerbersoft.mobilenews.data.utils.date.mapToString

internal fun ArticleEntity.mapToArticle(): Article =
    Article(
        articleId = articleId,
        author = author,
        content = content,
        description = description,
        publishedAt = publishedAt,
        source = Source(sourceId, source),
        title = title,
        url = url,
        imageUrl = imageUrl,
        isBookmarked = isBookmarked
    )

internal fun ArticleResponse.mapToArticleEntity(): ArticleEntity =
    ArticleEntity(
        articleId = url.hashCode().toString(),
        author = author ?: "",
        content = content ?: "",
        description = description ?: "",
        publishedAt = publishedAt?.mapToString("MMM dd hh:mm") ?: "",
        source = source.name,
        sourceId = source.id,
        title = title,
        url = url,
        imageUrl = imageUrl ?: "",
        isBookmarked = false
    )

internal fun SourceResponse.map(): Source =
    Source(id = id, name = name)