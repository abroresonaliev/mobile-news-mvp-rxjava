package uz.icerbersoft.mobilenews.data.datasource.database.dao.article

import androidx.room.Dao
import androidx.room.Query
import io.reactivex.Observable
import uz.icerbersoft.mobilenews.data.datasource.database.base.BaseDao
import uz.icerbersoft.mobilenews.data.model.article.ArticleEntity

@Dao
internal abstract class ArticleEntityDao : BaseDao<ArticleEntity>() {

    @Query("SELECT * FROM articles LIMIT 20")
    abstract fun getArticleEntities(): Observable<List<ArticleEntity>>

    @Query("SELECT * FROM articles WHERE article_is_bookmarked = :isBookmarked LIMIT 20")
    abstract fun getArticleEntitiesByBookmark(isBookmarked: Boolean): Observable<List<ArticleEntity>>

    @Query("SELECT * FROM articles WHERE article_article_id = :id")
    abstract fun getArticleEntityById(id: Long): Observable<ArticleEntity>

    @Query("UPDATE articles SET article_is_bookmarked = :isBookmarked WHERE article_article_id = :articleId")
    abstract fun updateBookmark(articleId: Long, isBookmarked: Boolean)
}