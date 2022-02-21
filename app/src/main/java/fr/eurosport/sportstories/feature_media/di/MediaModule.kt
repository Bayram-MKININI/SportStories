package fr.eurosport.sportstories.feature_media.di

import android.content.Context
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import fr.eurosport.sportstories.common.BASE_URL
import fr.eurosport.sportstories.feature_media.data.local.MediaDatabase
import fr.eurosport.sportstories.feature_media.data.remote.MediaApi
import fr.eurosport.sportstories.feature_media.data.remote.dto.TimestampAdapter
import fr.eurosport.sportstories.feature_media.data.repository.MediaRepositoryImpl
import fr.eurosport.sportstories.feature_media.domain.repository.MediaRepository
import fr.eurosport.sportstories.feature_media.domain.use_cases.GetMedia
import fr.eurosport.sportstories.feature_media.presentation.MediaViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val appModule = module {
    single { provideOkHttpClient() }
    single { provideRetrofit(okHttpClient = get()) }
    single { provideApiService(retrofit = get()) }
    single { provideDatabase(context = androidContext()) }
    single { provideMediaRepository(api = get(), db = get()) }
    single { provideGetMediaUseCase(repository = get()) }
}

fun provideGetMediaUseCase(repository: MediaRepository): GetMedia {
    return GetMedia(repository)
}

private fun provideOkHttpClient() = if (org.koin.android.BuildConfig.DEBUG) {

    val loggingInterceptor = HttpLoggingInterceptor()
    loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
    OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .followRedirects(true)
        .build()

} else OkHttpClient
    .Builder()
    .followRedirects(true)
    .build()

private fun provideRetrofit(
    okHttpClient: OkHttpClient
): Retrofit = Retrofit.Builder()
    .addConverterFactory(
        MoshiConverterFactory.create(
            Moshi.Builder().addLast(KotlinJsonAdapterFactory()).add(TimestampAdapter()).build()
        )
    )
    .baseUrl(BASE_URL)
    .client(okHttpClient)
    .build()

private fun provideApiService(retrofit: Retrofit): MediaApi = retrofit.create(MediaApi::class.java)

private fun provideDatabase(context: Context): MediaDatabase = MediaDatabase(context)

private fun provideMediaRepository(
    api: MediaApi,
    db: MediaDatabase
): MediaRepository {
    return MediaRepositoryImpl(api, db.dao)
}

val viewModelModule = module {
    viewModel {
        MediaViewModel(
            getMedia = get()
        )
    }
}