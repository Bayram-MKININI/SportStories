package fr.eurosport.sportstories.feature_media.domain.model

sealed class MediaElement {
    abstract val id: Int
    abstract val title: String
    abstract val sport: Sport
    abstract val dateTimestamp: Long

    data class Story(
        override val id: Int = 0,
        override val title: String = "",
        override val dateTimestamp: Long = 0,
        override val sport: Sport = Sport(),
        val author: String = "",
        val imageUrl: String = "",
        val teaser: String = "",
    ) : MediaElement()

    data class Video(
        override val id: Int = 0,
        override val title: String = "",
        override val dateTimestamp: Long = 0,
        override val sport: Sport = Sport(),
        val videoUrl: String = "",
        val thumbUrl: String = "",
        val viewCount: Int = 0,
    ) : MediaElement()
}