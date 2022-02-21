package fr.eurosport.sportstories.feature_media.domain.model

data class Video(
    val id: Int = 0,
    val title: String = "",
    val videoUrl: String = "",
    val dateTimestamp: Long = 0,
    val sport: Sport = Sport(),
    val thumbUrl: String = "",
    val viewCount: Int = 0
) : MediaElement