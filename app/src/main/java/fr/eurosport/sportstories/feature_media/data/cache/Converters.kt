package fr.eurosport.sportstories.feature_media.data.cache

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.squareup.moshi.Moshi
import fr.eurosport.sportstories.feature_media.data.cache.entity.SportEntity

@ProvidedTypeConverter
class Converters(
    private val moshi: Moshi
) {

    @TypeConverter
    fun fromSportEntity(data: SportEntity): String {
        return moshi.adapter(SportEntity::class.java).toJson(data)
    }

    @TypeConverter
    fun toSportEntity(json: String): SportEntity {
        return moshi.adapter(SportEntity::class.java).fromJson(json) ?: SportEntity()
    }
}