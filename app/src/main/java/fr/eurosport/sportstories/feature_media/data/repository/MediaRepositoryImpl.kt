package fr.eurosport.sportstories.feature_media.data.repository

import fr.eurosport.sportstories.common.util.DataError
import fr.eurosport.sportstories.common.util.Resource
import fr.eurosport.sportstories.feature_media.data.local.MediaDAO
import fr.eurosport.sportstories.feature_media.data.remote.MediaApi
import fr.eurosport.sportstories.feature_media.domain.model.Media
import fr.eurosport.sportstories.feature_media.domain.repository.MediaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException

class MediaRepositoryImpl(
    private val api: MediaApi,
    private val dao: MediaDAO
) : MediaRepository {

    override fun getMedia(): Flow<Resource<Media>> = flow {

        val stories = dao.getCachedStories().map { it.toStory() }
        val videos = dao.getCachedVideos().map { it.toVideo() }

        emit(Resource.Loading(data = Media(stories, videos)))

        try {
            val remoteMedia = api.fetchMediaResponse()
            dao.insertStories(remoteMedia.storyDTOs.map { it.toStoryEntity() })
            dao.insertVideos(remoteMedia.videoDTOs.map { it.toVideoEntity() })

        } catch (ex: HttpException) {

            emit(Resource.Error(dataError = DataError.SYSTEM_ERROR, data = Media(stories, videos)))

        } catch (ex: IOException) {

            emit(Resource.Error(dataError = DataError.NETWORK_ERROR, data = Media(stories, videos)))
        }

        val newStories = dao.getCachedStories().map { it.toStory() }
        val newVideos = dao.getCachedVideos().map { it.toVideo() }
        emit(Resource.Success(data = Media(newStories, newVideos)))
    }
}