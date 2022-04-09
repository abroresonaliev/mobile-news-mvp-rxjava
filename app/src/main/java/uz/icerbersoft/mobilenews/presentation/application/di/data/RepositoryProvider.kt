package uz.icerbersoft.mobilenews.presentation.application.di.data

import uz.icerbersoft.mobilenews.domain.data.repository.article.ArticleRepository

interface RepositoryProvider {

    val articleRepository: ArticleRepository
}