package uz.icerbersoft.mobilenews.data.datasource.database.dao.article

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import io.reactivex.Observable
import kotlinx.coroutines.flow.Flow
import uz.icerbersoft.mobilenews.data.datasource.database.base.BaseDao
import uz.icerbersoft.mobilenews.data.model.article.ArticleEntity

@Dao
internal abstract class ArticleEntityDao : BaseDao<ArticleEntity>() {

    @Query("SELECT * FROM articles ORDER BY article_article_id DESC LIMIT 20")
    abstract fun getArticleEntities(): Observable<List<ArticleEntity>>

    @Query("SELECT * FROM articles WHERE article_url in (:urls) ORDER BY article_article_id DESC LIMIT 20")
    abstract fun getArticleEntitiesByUrl(urls: Array<String>): Observable<List<ArticleEntity>>

    @Query("SELECT * FROM articles WHERE article_is_bookmarked = :isBookmarked ORDER BY article_article_id DESC LIMIT 20")
    abstract fun getArticleEntitiesByBookmark(isBookmarked: Boolean): Observable<List<ArticleEntity>>

    @Query("SELECT * FROM articles WHERE article_article_id = :articleId")
    abstract fun getArticleEntityById(articleId: String): Observable<ArticleEntity>

    @Query("SELECT * FROM articles WHERE article_article_id = :articleId")
    abstract fun getArticleEntityByIdWithoutFlow(articleId: String): ArticleEntity?

    @Query("UPDATE articles SET article_is_bookmarked = :isBookmarked WHERE article_article_id = :articleId")
    abstract fun updateBookmark(articleId: String, isBookmarked: Boolean)

    @Transaction
    open fun updateArticle(articleEntity: ArticleEntity) {
        val entity = getArticleEntityByIdWithoutFlow(articleEntity.articleId)
        update(articleEntity.copy(isBookmarked = entity?.isBookmarked ?: false))
    }
}