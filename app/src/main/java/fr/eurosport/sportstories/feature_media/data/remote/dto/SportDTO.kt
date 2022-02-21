package fr.eurosport.sportstories.feature_media.data.remote.dto


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import fr.eurosport.sportstories.feature_media.data.local.entity.SportEntity

@JsonClass(generateAdapter = true)
data class SportDTO(
    @Json(name = "id")
    val id: Int = 0,
    @Json(name = "name")
    val name: String = ""
){
    fun toSportEntity(): SportEntity {
        return SportEntity(
            id = id,
            name = name
        )
    }
}