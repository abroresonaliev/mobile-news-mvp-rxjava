package uz.icerbersoft.mobilenews.presentation.application.di

import dagger.Module
import uz.icerbersoft.mobilenews.presentation.application.di.data.DataDaggerModuleDataSource
import uz.icerbersoft.mobilenews.presentation.application.di.data.DataDaggerModulePreference
import uz.icerbersoft.mobilenews.presentation.application.di.data.DataDaggerModuleRepository
import uz.icerbersoft.mobilenews.presentation.application.di.domain.DomainDaggerModuleUseCase

@Module(includes = [
    ApplicationDaggerModuleManager::class,
    DataDaggerModuleDataSource::class,
    DataDaggerModulePreference::class,
    DataDaggerModuleRepository::class,
    DomainDaggerModuleUseCase::class
])
object ApplicationDaggerModule