package uz.icerbersoft.mobilenews.presentation.presentation.home.features.dashboard

import android.os.Bundle
import android.view.View
import dagger.Lazy
import me.vponomarenko.injectionmanager.x.XInjectionManager
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import ru.surfstudio.android.easyadapter.EasyAdapter
import ru.surfstudio.android.easyadapter.ItemList
import uz.icerbersoft.mobilenews.R
import uz.icerbersoft.mobilenews.data.utils.date.toFormattedDate
import uz.icerbersoft.mobilenews.databinding.FragmentDashboardArticlesBinding
import uz.icerbersoft.mobilenews.domain.data.model.article.ArticleWrapper
import uz.icerbersoft.mobilenews.presentation.global.GlobalActivity
import uz.icerbersoft.mobilenews.presentation.presentation.home.features.dashboard.controller.BreakingArticleItemController
import uz.icerbersoft.mobilenews.presentation.presentation.home.features.dashboard.controller.TopArticleItemController
import uz.icerbersoft.mobilenews.presentation.support.controller.StateEmptyItemController
import uz.icerbersoft.mobilenews.presentation.support.controller.StateErrorItemController
import uz.icerbersoft.mobilenews.presentation.support.controller.StateLoadingItemController
import uz.icerbersoft.mobilenews.presentation.utils.addCallback
import java.util.*
import javax.inject.Inject

internal class DashboardArticlesFragment :
    MvpAppCompatFragment(R.layout.fragment_dashboard_articles),
    DashboardArticlesView {

    @Inject
    lateinit var lazyPresenter: Lazy<DashboardArticlesPresenter>
    private val presenter by moxyPresenter { lazyPresenter.get() }

    private lateinit var binding: FragmentDashboardArticlesBinding

    private val breakingArticlesAdapter = EasyAdapter()
    private val breakingArticleController = BreakingArticleItemController(
        itemClickListener = { presenter.openArticleDetail(it) },
        bookmarkListener = { presenter.updateBookmark(it) }
    )
    private val breakingLoadingController = StateLoadingItemController(true)
    private val breakingEmptyController = StateEmptyItemController(true)
    private val breakingErrorController =
        StateErrorItemController(true) { presenter.getBreakingArticles() }

    private val topArticlesAdapter = EasyAdapter()
    private val topArticleController = TopArticleItemController(
        itemClickListener = { presenter.openArticleDetail(it) },
        bookmarkListener = { presenter.updateBookmark(it) }
    )
    private val topLoadingController = StateLoadingItemController(true)
    private val topEmptyController = StateEmptyItemController(true)
    private val topErrorController =
        StateErrorItemController(true) { presenter.getTopArticles() }

    override fun onCreate(savedInstanceState: Bundle?) {
        (requireActivity() as GlobalActivity)
            .globalDaggerComponent
            .inject(this)

        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            requireActivity().finish()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDashboardArticlesBinding.bind(view)
        with(binding) {
            todayDateTv.text = Date().toFormattedDate("EEEE, dd MMMM")
            breakingArticleRv.adapter = breakingArticlesAdapter
            breakingArticleRv.itemAnimator = null
            topArticleRv.adapter = topArticlesAdapter
            topArticleRv.itemAnimator = null
        }
    }

    override fun onDefinedBreakingArticleWrappers(articles: List<ArticleWrapper>) {
        val itemList = ItemList.create()
        for (item in articles) {
            when (item) {
                is ArticleWrapper.ArticleItem -> itemList.add(item, breakingArticleController)
                is ArticleWrapper.EmptyItem -> itemList.add(breakingEmptyController)
                is ArticleWrapper.ErrorItem -> itemList.add(breakingErrorController)
                is ArticleWrapper.LoadingItem -> itemList.add(breakingLoadingController)
            }
        }
        breakingArticlesAdapter.setItems(itemList)
    }

    override fun onDefinedTopArticleWrappers(articles: List<ArticleWrapper>) {
        val itemList = ItemList.create()
        for (item in articles) {
            when (item) {
                is ArticleWrapper.ArticleItem -> itemList.add(item, topArticleController)
                is ArticleWrapper.EmptyItem -> itemList.add(topEmptyController)
                is ArticleWrapper.ErrorItem -> itemList.add(topErrorController)
                is ArticleWrapper.LoadingItem -> itemList.add(topLoadingController)
            }
        }
        topArticlesAdapter.setItems(itemList)
    }

    companion object {

        fun newInstance() = DashboardArticlesFragment()
    }
}