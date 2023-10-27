package fr.eurosport.sportstories.feature_media.domain.model

import fr.eurosport.sportstories.feature_media.domain.model.MediaElement.*

data class Media(
    val stories: List<Story> = listOf(),
    val videos: List<Video> = listOf()
)