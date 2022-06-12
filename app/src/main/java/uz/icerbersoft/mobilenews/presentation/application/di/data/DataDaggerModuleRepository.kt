package uz.icerbersoft.mobilenews.presentation.application.di.data

import dagger.Binds
import dagger.Module
import uz.icerbersoft.mobilenews.data.repository.article.ArticleRepositoryImpl
import uz.icerbersoft.mobilenews.data.repository.settings.SettingsRepositoryImpl
import uz.icerbersoft.mobilenews.domain.data.repository.article.ArticleRepository
import uz.icerbersoft.mobilenews.domain.data.repository.settings.SettingsRepository

@Module
internal interface DataDaggerModuleRepository {

    @Binds
    fun articleRepository(
        impl: ArticleRepositoryImpl
    ): ArticleRepository

    @Binds
    fun bindSettingsRepository(
        impl: SettingsRepositoryImpl
    ): SettingsRepository
}