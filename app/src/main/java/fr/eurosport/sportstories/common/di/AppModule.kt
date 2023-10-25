package fr.eurosport.sportstories.common.di

import android.content.Context
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import fr.eurosport.sportstories.BuildConfig
import fr.eurosport.sportstories.common.BASE_ENDPOINT
import fr.eurosport.sportstories.common.data.remote.RemoteApi
import fr.eurosport.sportstories.feature_media.data.cache.MediaDAO
import fr.eurosport.sportstories.feature_media.data.cache.MediaDatabase
import fr.eurosport.sportstories.feature_media.data.remote.dto.TimestampAdapter
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideOkHttpClient() = if (BuildConfig.DEBUG) {
        OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            )
            .followRedirects(true)
            .build()
    } else OkHttpClient.Builder()
        .followRedirects(true)
        .build()

    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit.Builder = Retrofit.Builder()
        .baseUrl(BASE_ENDPOINT)
        .client(okHttpClient)
        .addConverterFactory(
            MoshiConverterFactory.create(
                Moshi.Builder()
                    .add(TimestampAdapter())
                    .addLast(KotlinJsonAdapterFactory())
                    .build()
            )
        )

    @Provides
    @Singleton
    fun provideApi(
        builder: Retrofit.Builder
    ): RemoteApi = builder.build().create(RemoteApi::class.java)

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext
        context: Context
    ): MediaDatabase = MediaDatabase.invoke(context)

    @Provides
    @Singleton
    fun provideDao(
        database: MediaDatabase
    ): MediaDAO = database.dao
}