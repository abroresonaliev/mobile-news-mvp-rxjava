package uz.icerbersoft.mobilenews.presentation.presentation.home.features.dashboard.controller

import android.view.ViewGroup
import ru.surfstudio.android.easyadapter.controller.BindableItemController
import ru.surfstudio.android.easyadapter.holder.BindableViewHolder
import uz.icerbersoft.mobilenews.R
import uz.icerbersoft.mobilenews.databinding.ViewHolderRecommendedArticleBinding
import uz.icerbersoft.mobilenews.domain.data.model.article.Article
import uz.icerbersoft.mobilenews.domain.data.model.article.ArticleWrapper.ArticleItem

internal class BreakingArticleItemController(
    private val itemClickListener: (product: Article) -> Unit,
    private val bookmarkListener: (Article) -> Unit
) : BindableItemController<ArticleItem, BreakingArticleItemController.Holder>() {

    override fun createViewHolder(parent: ViewGroup): Holder = Holder(parent)

    override fun getItemId(data: ArticleItem) = "$ID_TAG${data.article.articleId}"

    inner class Holder(parent: ViewGroup) :
        BindableViewHolder<ArticleItem>(parent, R.layout.view_holder_breaking_article) {

        private lateinit var article: Article
        private val binding = ViewHolderRecommendedArticleBinding.bind(itemView)

        init {
            with(binding) {
                itemParent.setOnClickListener { itemClickListener.invoke(article) }
                bookmarkImageView.setOnClickListener { bookmarkListener.invoke(article) }
            }
        }

        override fun bind(data: ArticleItem) {
            article = data.article
            with(binding) {
                titleTextView.text = data.article.title
                sourceTextView.text = data.article.source.name
                publishedAtTextView.text = data.article.publishedAt
                imageSimpleImageView.setImageURI(data.article.imageUrl)
                with(bookmarkImageView) {
                    if (data.article.isBookmarked) setImageResource(R.drawable.ic_bookmark)
                    else setImageResource(R.drawable.ic_bookmark_border)
                }
            }
        }
    }

    private companion object {
        const val ID_TAG = "BreakingArticleItemController"
    }
}