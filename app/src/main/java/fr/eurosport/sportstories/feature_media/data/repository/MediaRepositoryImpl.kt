package fr.eurosport.sportstories.feature_media.data.repository

import fr.eurosport.sportstories.common.data.remote.RemoteApi
import fr.eurosport.sportstories.common.util.ErrorType.NETWORK_ERROR
import fr.eurosport.sportstories.common.util.ErrorType.SYSTEM_ERROR
import fr.eurosport.sportstories.common.util.Resource
import fr.eurosport.sportstories.common.util.Resource.Error
import fr.eurosport.sportstories.common.util.Resource.Loading
import fr.eurosport.sportstories.common.util.Resource.Success
import fr.eurosport.sportstories.feature_media.data.cache.MediaDAO
import fr.eurosport.sportstories.feature_media.domain.model.Media
import fr.eurosport.sportstories.feature_media.domain.repository.MediaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class MediaRepositoryImpl @Inject constructor(
    private val api: RemoteApi,
    private val dao: MediaDAO
) : MediaRepository {

    override fun getMedia(): Flow<Resource<Media>> = flow {

        emit(Loading())
        val stories = dao.getCachedStories().map { it.toStory() }
        val videos = dao.getCachedVideos().map { it.toVideo() }
        emit(Loading(data = Media(stories, videos)))

        try {
            val remoteMedia = api.fetchMediaResponse()
            val storiesInserts = dao.insertStories(
                remoteMedia.storyDTOs.map { it.toStoryEntity() }
            )
            val videosInserts = dao.insertVideos(
                remoteMedia.videoDTOs.map { it.toVideoEntity() }
            )
            storiesInserts.plus(videosInserts).filter {
                it > 0
            }.also { inserts ->
                if (inserts.isNotEmpty()) {
                    val newStories = dao.getCachedStories().map { it.toStory() }
                    val newVideos = dao.getCachedVideos().map { it.toVideo() }
                    emit(Success(data = Media(newStories, newVideos)))
                }
            }

        } catch (ex: HttpException) {
            emit(Error(dataError = SYSTEM_ERROR))
        } catch (ex: IOException) {
            emit(Error(dataError = NETWORK_ERROR))
        }
    }
}