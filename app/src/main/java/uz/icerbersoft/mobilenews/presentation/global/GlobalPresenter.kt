package uz.icerbersoft.mobilenews.presentation.global

import moxy.MvpPresenter
import uz.icerbersoft.mobilenews.presentation.global.router.GlobalRouter
import javax.inject.Inject

class GlobalPresenter @Inject constructor(
    private val globalRouter: GlobalRouter
) : MvpPresenter<GlobalView>() {

    override fun onFirstViewAttach() {
        globalRouter.openHomeScreen()
    }
}