package uz.icerbersoft.mobilenews.presentation.application.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import uz.icerbersoft.mobilenews.presentation.application.Application
import uz.icerbersoft.mobilenews.presentation.application.manager.daynight.DayNightModeManager
import uz.icerbersoft.mobilenews.presentation.global.di.GlobalDaggerComponent
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationDaggerModule::class])
internal interface ApplicationDaggerComponent {

    fun inject(application: Application)

    val globalDaggerComponent: GlobalDaggerComponent.Factory

    val dayNightModeManager: DayNightModeManager

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): ApplicationDaggerComponent
    }

    companion object {
        fun create(context: Context): ApplicationDaggerComponent =
            DaggerApplicationDaggerComponent
                .factory()
                .create(context)
    }
}