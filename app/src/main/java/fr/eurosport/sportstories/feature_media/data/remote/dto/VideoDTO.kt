package fr.eurosport.sportstories.feature_media.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import fr.eurosport.sportstories.feature_media.data.local.entity.VideoEntity

@JsonClass(generateAdapter = true)
data class VideoDTO(
    @Json(name = "id")
    val id: Int = 0,
    @Json(name = "date")
    @Timestamp
    val date: Long = 0,
    @Json(name = "sport")
    val sport: SportDTO = SportDTO(),
    @Json(name = "thumb")
    val thumb: String = "",
    @Json(name = "title")
    val title: String = "",
    @Json(name = "url")
    val url: String = "",
    @Json(name = "views")
    val views: Int = 0
) {
    fun toVideoEntity(): VideoEntity {
        return VideoEntity(
            id = id,
            title = title,
            videoUrl = url,
            dateTimestamp = date,
            sport = sport.toSportEntity(),
            thumbUrl = thumb,
            viewCount = views
        )
    }
}