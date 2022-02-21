package fr.eurosport.sportstories.feature_media.data.remote.dto


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MediaResponseDTO(

    @Json(name = "stories")
    val storyDTOs: List<StoryDTO> = listOf(),

    @Json(name = "videos")
    val videoDTOs: List<VideoDTO> = listOf()
)