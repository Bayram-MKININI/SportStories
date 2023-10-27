package fr.eurosport.sportstories.feature_media.data.cache.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import fr.eurosport.sportstories.feature_media.domain.model.MediaElement.*

@Entity
data class VideoEntity(
    @PrimaryKey val id: Int = 0,
    val dateTimestamp: Long = 0,
    val sport: SportEntity = SportEntity(),
    val thumbUrl: String = "",
    val title: String = "",
    val videoUrl: String = "",
    val viewCount: Int = 0
) {
    fun toVideo() = Video(
        id = id,
        dateTimestamp = dateTimestamp,
        sport = sport.toSport(),
        thumbUrl = thumbUrl,
        title = title,
        videoUrl = videoUrl,
        viewCount = viewCount
    )
}