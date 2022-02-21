package fr.eurosport.sportstories.feature_media.domain.model


data class Media(
    val stories: List<Story> = listOf(),
    val videos: List<Video> = listOf()
)