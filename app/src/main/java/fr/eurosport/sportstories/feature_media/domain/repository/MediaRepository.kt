package fr.eurosport.sportstories.feature_media.domain.repository

import fr.eurosport.sportstories.common.util.Resource
import fr.eurosport.sportstories.feature_media.domain.model.Media
import kotlinx.coroutines.flow.Flow

interface MediaRepository {

    fun getMedia(): Flow<Resource<Media>>
}