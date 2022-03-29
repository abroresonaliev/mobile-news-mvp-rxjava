package uz.icerbersoft.mobilenews.domain.usecase.common.bookmark

import io.reactivex.Observable
import uz.icerbersoft.mobilenews.domain.data.model.article.Article

interface BookmarkUseCase {

    fun updateBookmark(article: Article): Observable<Unit>
}