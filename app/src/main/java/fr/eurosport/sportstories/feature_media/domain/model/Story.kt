package fr.eurosport.sportstories.feature_media.domain.model

data class Story(
    val id: Int = 0,
    val title: String = "",
    val author: String = "",
    val dateTimestamp: Long = 0,
    val imageUrl: String = "",
    val sport: Sport = Sport(),
    val teaser: String = ""
) : MediaElement
