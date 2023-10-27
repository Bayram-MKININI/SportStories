package fr.eurosport.sportstories.feature_media.data.repository

import fr.eurosport.sportstories.common.util.Resource
import fr.eurosport.sportstories.feature_media.domain.model.Media
import fr.eurosport.sportstories.feature_media.domain.repository.MediaRepository
import fr.eurosport.sportstories.feature_media.fixtures.MediaElementFixtures
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeMediaRepository : MediaRepository {
    override fun getMedia(): Flow<Resource<Media>> = flow {
        emit(Resource.Success(data = MediaElementFixtures.buildMedia()))
    }
}