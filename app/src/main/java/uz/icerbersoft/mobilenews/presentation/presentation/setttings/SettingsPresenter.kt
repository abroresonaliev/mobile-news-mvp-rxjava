package uz.icerbersoft.mobilenews.presentation.presentation.setttings

import uz.icerbersoft.mobilenews.domain.data.entity.settings.DayNightModeWrapper
import uz.icerbersoft.mobilenews.domain.usecase.daynight.DayNightModeUseCase
import uz.icerbersoft.mobilenews.presentation.presentation.setttings.router.SettingsRouter
import uz.icerbersoft.mobilenews.presentation.support.event.LoadingListEvent.*
import uz.icerbersoft.mobilenews.presentation.support.moxy.BaseMoxyPresenter
import uz.icerbersoft.mobilenews.presentation.utils.convertToAppDelegateModeNight
import javax.inject.Inject

class SettingsPresenter @Inject constructor(
    private val useCase: DayNightModeUseCase,
    private val router: SettingsRouter
) : BaseMoxyPresenter<SettingsView>() {

    private val dayNightModeWrappers: MutableList<DayNightModeWrapper> = mutableListOf()

    override fun onFirstViewAttach() =
        getAvailableSettings()

    fun getAvailableSettings() {
        val disposable = useCase.getDayNightModWrappers()
            .doOnSubscribe { viewState.onDefinedDayNightModeWrappers(LoadingState) }
            .subscribe(
                {
                    dayNightModeWrappers.clear()
                    dayNightModeWrappers.addAll(it)

                    if (it.isNotEmpty()) viewState.onDefinedDayNightModeWrappers(SuccessState(it))
                    else viewState.onDefinedDayNightModeWrappers(EmptyState)
                },
                { viewState.onDefinedDayNightModeWrappers(ErrorState(it.localizedMessage)) }
            )

        compositeDisposable.add(disposable)
    }

    fun saveDayNightMode(dayNightModeWrapper: DayNightModeWrapper) {
        useCase.setDayNightMode(dayNightModeWrapper.dayNightMode.convertToAppDelegateModeNight())

        dayNightModeWrappers.forEach {
            it.isSelected = it.dayNightMode == dayNightModeWrapper.dayNightMode
        }

        viewState.onDefinedDayNightModeWrappers(SuccessState(dayNightModeWrappers))
    }

    fun back() = router.back()
}