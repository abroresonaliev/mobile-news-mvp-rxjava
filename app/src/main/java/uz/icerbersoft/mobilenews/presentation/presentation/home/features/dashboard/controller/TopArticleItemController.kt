package uz.icerbersoft.mobilenews.presentation.presentation.home.features.dashboard.controller

import android.view.ViewGroup
import ru.surfstudio.android.easyadapter.controller.BindableItemController
import ru.surfstudio.android.easyadapter.holder.BindableViewHolder
import uz.icerbersoft.mobilenews.R
import uz.icerbersoft.mobilenews.databinding.ViewHolderTopArticleBinding
import uz.icerbersoft.mobilenews.domain.data.entity.article.Article
import uz.icerbersoft.mobilenews.domain.data.entity.article.ArticleWrapper.ArticleItem

internal class TopArticleItemController(
    private val itemClickListener: (product: Article) -> Unit,
    private val bookmarkListener: (Article) -> Unit
) : BindableItemController<ArticleItem, TopArticleItemController.Holder>() {

    override fun createViewHolder(parent: ViewGroup): Holder = Holder(parent)

    override fun getItemId(data: ArticleItem) = "$ID_TAG${data.article.articleId}"

    inner class Holder(parent: ViewGroup) :
        BindableViewHolder<ArticleItem>(parent, R.layout.view_holder_top_article) {

        private lateinit var article: Article
        private val binding = ViewHolderTopArticleBinding.bind(itemView)

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
        const val ID_TAG = "TopArticleItemController"
    }
}