package uz.icerbersoft.mobilenews.presentation.presentation.detail

import android.content.Intent
import android.os.Bundle
import android.view.View
import dagger.Lazy
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import uz.icerbersoft.mobilenews.R
import uz.icerbersoft.mobilenews.databinding.FragmentArticleDetailBinding
import uz.icerbersoft.mobilenews.domain.data.entity.article.Article
import uz.icerbersoft.mobilenews.presentation.global.GlobalActivity
import uz.icerbersoft.mobilenews.presentation.support.event.LoadingEvent
import uz.icerbersoft.mobilenews.presentation.support.event.LoadingEvent.*

import uz.icerbersoft.mobilenews.presentation.utils.addCallback
import javax.inject.Inject

internal class ArticleDetailFragment : MvpAppCompatFragment(R.layout.fragment_article_detail),
    ArticleDetailView {

    @Inject
    lateinit var lazyPresenter: Lazy<ArticleDetailPresenter>
    private val presenter by moxyPresenter {
        lazyPresenter.get().apply {
            setArticleId(checkNotNull(arguments?.getString(KEY_ARTICLE_ID)))
        }
    }

    private lateinit var binding: FragmentArticleDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        (requireActivity() as GlobalActivity)
            .globalDaggerComponent
            .inject(this)

        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this) { presenter.back() }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentArticleDetailBinding.bind(view)

        with(binding) {
            backIv.setOnClickListener { requireActivity().onBackPressedDispatcher.onBackPressed() }
        }
    }

    override fun onDestroy() {
        presenter.clearPresenter()

        super.onDestroy()
    }

    override fun onSuccessArticleDetail(event: LoadingEvent<Article>) {
        with(binding) {
            when(event){
                is LoadingState -> {
                }
                is SuccessState -> {
                    detailImageSdv.setImageURI(event.data.imageUrl)
                    publishedAtTextView.text = event.data.publishedAt
                    titleTextView.text = event.data.title
                    sourceTextView.text = event.data.source.name
                    contentTextView.text = event.data.content

                    bookmarkIv.apply {
                        if (event.data.isBookmarked) setImageResource(R.drawable.ic_bookmark)
                        else setImageResource(R.drawable.ic_bookmark_border)
                    }

                    bookmarkIv.setOnClickListener { presenter.updateBookmark(event.data) }

                    shareIv.setOnClickListener {
                        val shareText =
                            "${event.data.title}\n\nMobile news - interesting news in your mobile.\n\n${event.data.url}"

                        val sendIntent: Intent = Intent().apply {
                            action = Intent.ACTION_SEND
                            putExtra(Intent.EXTRA_TEXT, shareText)
                            type = "text/plain"
                        }

                        val shareIntent = Intent.createChooser(sendIntent, "Share")
                        startActivity(shareIntent)
                    }
                }
                is ErrorState -> {
                }
            }
        }
    }

    companion object {

        private const val KEY_ARTICLE_ID: String = "key_article_id"

        fun newInstance(articleId: String) =
            ArticleDetailFragment().apply {
                arguments = Bundle().apply { putString(KEY_ARTICLE_ID, articleId) }
            }
    }
}