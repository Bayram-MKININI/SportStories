package fr.eurosport.sportstories.feature_media.data.remote.dto

import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonQualifier
import com.squareup.moshi.ToJson
import fr.eurosport.sportstories.common.util.parseTimestamp

@Retention(AnnotationRetention.RUNTIME)
@JsonQualifier
annotation class Timestamp

class TimestampAdapter {
    @FromJson
    @Timestamp
    fun fromJson(value: String): Long {
        return value.parseTimestamp()
    }

    @ToJson
    fun toJson(@Timestamp timestamp: Long): String {
        return timestamp.toString()
    }
}