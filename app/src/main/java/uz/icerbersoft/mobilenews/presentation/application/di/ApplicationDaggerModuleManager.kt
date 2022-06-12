package uz.icerbersoft.mobilenews.presentation.application.di

import dagger.Module
import dagger.Provides
import uz.icerbersoft.mobilenews.data.datasource.preference.DayNightModePreference
import uz.icerbersoft.mobilenews.presentation.application.manager.daynight.DayNightModeManager
import javax.inject.Singleton

@Module
internal object ApplicationDaggerModuleManager {

    @JvmStatic
    @Provides
    @Singleton
    fun dayNightModeManager(
        dayNightModePreference: DayNightModePreference
    ): DayNightModeManager = DayNightModeManager(dayNightModePreference)
}