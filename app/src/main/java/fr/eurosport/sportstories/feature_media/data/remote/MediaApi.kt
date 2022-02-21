package fr.eurosport.sportstories.feature_media.data.remote

import fr.eurosport.sportstories.feature_media.data.remote.dto.MediaResponseDTO
import retrofit2.http.GET

interface MediaApi {

    @GET("json-storage/bin/edfefba")
    suspend fun fetchMediaResponse(): MediaResponseDTO
}