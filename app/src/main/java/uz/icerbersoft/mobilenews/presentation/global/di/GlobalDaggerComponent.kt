package uz.icerbersoft.mobilenews.presentation.global.di

import dagger.Subcomponent
import uz.icerbersoft.mobilenews.presentation.global.GlobalActivity
import uz.icerbersoft.mobilenews.presentation.global.router.GlobalRouter
import uz.icerbersoft.mobilenews.presentation.presentation.detail.ArticleDetailFragment
import uz.icerbersoft.mobilenews.presentation.presentation.home.di.HomeDaggerComponent
import uz.icerbersoft.mobilenews.presentation.presentation.home.features.dashboard.DashboardArticlesFragment
import uz.icerbersoft.mobilenews.presentation.presentation.home.features.readlater.ReadLaterArticlesFragment
import uz.icerbersoft.mobilenews.presentation.presentation.home.features.recommended.RecommendedArticlesFragment
import uz.icerbersoft.mobilenews.presentation.presentation.home.router.HomeRouter
import uz.icerbersoft.mobilenews.presentation.presentation.setttings.SettingsFragment

@GlobalScope
@Subcomponent(
    modules = [
        GlobalDaggerModule::class,
        GlobalDaggerModuleNavigation::class,
        GlobalDaggerModuleSubComponents::class
    ]
)
internal interface GlobalDaggerComponent {

    val globalRouter: GlobalRouter
    val homeRouter: HomeRouter

    val homeDaggerComponent: HomeDaggerComponent.Factory

    fun inject(activity: GlobalActivity)
    fun inject(fragment: ArticleDetailFragment)
    fun inject(fragment: DashboardArticlesFragment)
    fun inject(fragment: ReadLaterArticlesFragment)
    fun inject(fragment: RecommendedArticlesFragment)
    fun inject(fragment: SettingsFragment)

    @Subcomponent.Factory
    interface Factory {
        fun create(): GlobalDaggerComponent
    }
}