package uz.icerbersoft.mobilenews.app.presentation.home.features.recommended.controller

import android.view.ViewGroup
import ru.surfstudio.android.easyadapter.controller.BindableItemController
import ru.surfstudio.android.easyadapter.holder.BindableViewHolder
import uz.icerbersoft.mobilenews.app.R
import uz.icerbersoft.mobilenews.app.databinding.ViewHolderRecommendedArticleBinding
import uz.icerbersoft.mobilenews.data.model.article.Article
import uz.icerbersoft.mobilenews.domain.interactor.article.detail.model.ArticleWrapper.ArticleItem

internal class RecommendedArticleItemController(
    private val itemClickListener: (product: Article) -> Unit,
    private val bookmarkListener: (Article) -> Unit
) : BindableItemController<ArticleItem, RecommendedArticleItemController.Holder>(){

    override fun createViewHolder(parent: ViewGroup): Holder = Holder(parent)

    override fun getItemId(data: ArticleItem) = "$ID_TAG${data.article.articleId}"

    inner class Holder(parent: ViewGroup) :
        BindableViewHolder<ArticleItem>(parent, R.layout.view_holder_recommended_article) {

        private lateinit var article: Article
        private val binding = ViewHolderRecommendedArticleBinding.bind(itemView)

        init {
            with(binding){
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
        const val ID_TAG = "RecommendedArticleItemController"
    }
}