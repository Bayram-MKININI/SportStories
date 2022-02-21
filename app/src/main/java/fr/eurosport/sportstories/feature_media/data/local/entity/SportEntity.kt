package fr.eurosport.sportstories.feature_media.data.local.entity


import androidx.room.Entity
import androidx.room.PrimaryKey
import fr.eurosport.sportstories.feature_media.domain.model.Sport

@Entity
data class SportEntity(
    @PrimaryKey val id: Int = 0,
    val name: String = ""
) {
    fun toSport(): Sport {
        return Sport(
            id = id,
            name = name
        )
    }
}