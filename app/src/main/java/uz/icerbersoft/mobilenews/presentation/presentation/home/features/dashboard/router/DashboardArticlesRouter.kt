package uz.icerbersoft.mobilenews.presentation.presentation.home.features.dashboard.router

import uz.icerbersoft.mobilenews.presentation.global.router.GlobalRouter
import uz.icerbersoft.mobilenews.presentation.presentation.home.router.HomeRouter
import uz.icerbersoft.mobilenews.presentation.support.cicerone.base.FeatureRouter
import javax.inject.Inject

class DashboardArticlesRouter @Inject constructor(
    private val globalRouter: GlobalRouter,
    private val homeRouter: HomeRouter
) : FeatureRouter()