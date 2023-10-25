package fr.eurosport.sportstories.feature_media.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import fr.eurosport.sportstories.feature_media.data.repository.MediaRepositoryImpl
import fr.eurosport.sportstories.feature_media.domain.repository.MediaRepository

@Module
@InstallIn(ViewModelComponent::class)
abstract class MediaModule {
    @Binds
    @ViewModelScoped
    abstract fun bindMediaRepository(
        alertsRepository: MediaRepositoryImpl
    ): MediaRepository
}