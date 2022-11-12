package uz.icerbersoft.mobilenews.domain.data.repository.settings

import io.reactivex.Observable
import uz.icerbersoft.mobilenews.domain.data.entity.settings.DayNightMode

interface SettingsRepository {

    fun getSelectedDayNightMode(): Observable<DayNightMode>

    fun getDayNightModes(): Observable<List<DayNightMode>>

    fun saveDayNightMode(value: DayNightMode)

}