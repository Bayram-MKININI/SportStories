package fr.eurosport.sportstories.feature_media.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import fr.eurosport.sportstories.feature_media.data.local.entity.StoryEntity

@JsonClass(generateAdapter = true)
data class StoryDTO(
    @Json(name = "author")
    val author: String = "",
    @Json(name = "date")
    @Timestamp
    val date: Long = 0,
    @Json(name = "id")
    val id: Int = 0,
    @Json(name = "image")
    val image: String = "",
    @Json(name = "sport")
    val sportDTO: SportDTO = SportDTO(),
    @Json(name = "teaser")
    val teaser: String = "",
    @Json(name = "title")
    val title: String = ""
) {
    fun toStoryEntity(): StoryEntity {

        return StoryEntity(
            id = id,
            author = author,
            dateTimestamp = date,
            imageUrl = image,
            sport = sportDTO.toSportEntity(),
            teaser = teaser,
            title = title
        )
    }
}
