package fr.eurosport.sportstories.feature_media.domain.use_cases

import fr.eurosport.sportstories.common.util.Resource
import fr.eurosport.sportstories.common.util.mixAlternate
import fr.eurosport.sportstories.feature_media.domain.model.MediaElement
import fr.eurosport.sportstories.feature_media.domain.repository.MediaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetMedia(
    private val repository: MediaRepository
) {
    operator fun invoke(): Flow<Resource<List<MediaElement>>> {

        return repository.getMedia().map { result ->

            var mediaList: List<MediaElement> = listOf()

            result.data?.let { media ->
                mediaList = media.stories.sortedByDescending { story -> story.dateTimestamp }
                    .mixAlternate(media.videos.sortedByDescending { video -> video.dateTimestamp })
            }

            when (result) {
                is Resource.Success -> {
                    Resource.Success(data = mediaList)
                }
                is Resource.Loading -> {
                    Resource.Loading(data = mediaList)
                }
                is Resource.Error -> {
                    Resource.Error(dataError = result.dataError, data = mediaList)
                }
            }
        }
    }
}