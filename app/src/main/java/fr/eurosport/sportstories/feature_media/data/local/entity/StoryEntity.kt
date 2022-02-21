package fr.eurosport.sportstories.feature_media.data.local.entity


import androidx.room.Entity
import androidx.room.PrimaryKey
import fr.eurosport.sportstories.feature_media.domain.model.Story

@Entity
data class StoryEntity(
    @PrimaryKey val id: Int = 0,
    val author: String = "",
    val dateTimestamp: Long = 0,
    val imageUrl: String = "",
    val sport: SportEntity = SportEntity(),
    val teaser: String = "",
    val title: String = ""
){
    fun toStory(): Story {
        return Story(
            id = id,
            author = author,
            dateTimestamp = dateTimestamp,
            imageUrl = imageUrl,
            sport = sport.toSport(),
            teaser = teaser,
            title = title
        )
    }
}