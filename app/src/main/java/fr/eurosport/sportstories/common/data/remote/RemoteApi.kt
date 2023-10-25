package fr.eurosport.sportstories.common.data.remote

import fr.eurosport.sportstories.feature_media.data.remote.dto.MediaResponseDTO
import retrofit2.http.GET

interface RemoteApi {
    @GET("json-storage/bin/edfefba")
    suspend fun fetchMediaResponse(): MediaResponseDTO
}