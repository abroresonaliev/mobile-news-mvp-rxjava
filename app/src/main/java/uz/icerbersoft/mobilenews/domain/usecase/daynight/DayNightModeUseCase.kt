package uz.icerbersoft.mobilenews.domain.usecase.daynight

import io.reactivex.Observable
import uz.icerbersoft.mobilenews.domain.data.entity.settings.DayNightModeWrapper

interface DayNightModeUseCase {

    fun getDayNightModWrappers(): Observable<List<DayNightModeWrapper>>

    fun setDayNightMode(dayNightMode: Int)
}